import argparse
import os
import pprint
import subprocess
import sys
import unittest

debug = True
cli = None

class TestIntegration(unittest.TestCase):
    def setUp(self):
        cli.write(".some.path", "hello\n\nthis is a message\n")
        
    def tearDown(self):
        cli.rm(".some.path")

    def test_open_and_cat(self):
        global cli
        self.assertEqual("hello\n\nthis is a message\n", cli.cat(".some.path"))

    def test_stat(self):
        global cli
        cli.label(".some.path", "some", "label")
        self.assertRegex(cli.stat(".some.path"),
                         "path:.*\ncreated:  2020.*\nmodified: 2020.*\nlabels:.*\n  some: label\n")

    def test_list(self):
        global cli
        cli.write(".some.other.path", "hello\n\nthis is a message\n")
        cli.write(".some.really.other.path", "hello\n\nthis is a message\n")

        self.assertEqual(cli.list("."),
                         ".some.other.path\n.some.path\n.some.really.other.path\n")
        self.assertEqual(cli.list(".some"),
                         ".some.other.path\n.some.path\n.some.really.other.path\n")
        self.assertEqual(cli.list(".some.path"),
                         ".some.path\n")

    def test_grep(self):
        global cli
        cli.write(".some.other.path", "hello\n\nthis is a message 1\n")
        cli.write(".some.really.other.path", "hello\n\nthis is a message 2\n")

        self.assertRegex(cli.grep("message", ""),
                         ".some.other.path:.*\n.some.path:.*\n.some.really.other.path:.*\n")
        self.assertRegex(cli.grep("message", "."),
                         ".some.other.path:.*\n.some.path:.*\n.some.really.other.path:.*\n")
        self.assertRegex(cli.grep("message", ".some.path"),
                         ".some.path:.*\n")
        self.assertRegex(cli.grep("[12]", ""),
                         ".some.other.path:.*\n.some.really.other.path:.*\n")

class Cli:
    def __init__(self, directory, scheme, host, debug, username, password):
        self.directory = directory
        self.scheme = scheme
        self.host = host
        self.debug = debug
        self.username = username
        self.password = password

    def __str__(self):
        return "Cli(directory=%s, url=%s://%s:%s@%s)" % (self.directory,
                                                         self.scheme,
                                                         self.username,
                                                         self.password, self.host)

    def write(self, path, data):
        env = {}
        env['EDITOR'] = './fake_editor.sh'
        env['FAKE_EDITOR_OUTPUT'] = data
        return self.__run(['open', path], env=env)

    def cat(self, path):
        return self.__run(['cat', path])

    def rm(self, path):
        return self.__run(['rm', path])

    def stat(self, path):
        return self.__run(['stat', path])

    def label(self, path, key, value):
        return self.__run(['label', path, key, value])

    def list(self, prefix):
        return self.__run(['list', prefix])

    def grep(self, regex, prefix):
        return self.__run(['grep', regex, prefix])

    def __run(self, args, env={}):
        cmd = ['bundle', 'exec', 'anblog']
        cmd += args

        env['PATH'] = os.environ['PATH']
        env['ANBLOG_SCHEME'] = self.scheme
        env['ANBLOG_HOST'] = self.host
        if self.debug:
            env['ANBLOG_DEBUG'] = "true"
        env['ANBLOG_USERNAME'] = self.username
        env['ANBLOG_PASSWORD'] = self.password

        log('running:', pprint.pformat(cmd), 'with env:', pprint.pformat(env))
        p = subprocess.run(
            cmd, timeout=3, stdout=subprocess.PIPE, stderr=subprocess.PIPE,
            env=env, cwd=self.directory)
        log('stdout:', pprint.pformat(p.stdout))
        log('stderr:', pprint.pformat(p.stderr))
        p.check_returncode()
        return p.stdout.decode()

def log(*args):
    """Log a message to stderr if DEBUG global is set."""
    if debug:
        print("integration.py:", "debug:", *args, file=sys.stderr)

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--directory", default="client")
    parser.add_argument("--scheme", default="http")
    parser.add_argument("--host", default="localhost:8080")
    parser.add_argument("--debug", action='store_true', default=False)
    parser.add_argument("--username", default='anblog_default_username')
    parser.add_argument("--password", default='anblog_default_password')

    args = parser.parse_args()

    global debug
    debug = args.debug

    global cli
    cli = Cli(args.directory, args.scheme, args.host,
              args.debug, args.username, args.password)
    log("cli:", cli)
    suite = unittest.TestLoader().loadTestsFromTestCase(TestIntegration)
    result = unittest.TextTestRunner().run(suite)
    if len(result.failures) > 0 or len(result.errors) > 0:
        return 1
    else:
        return 0

if __name__ == '__main__':
    exit(main())

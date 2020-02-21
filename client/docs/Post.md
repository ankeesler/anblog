# Anblog::Post

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**path** | **String** | Unique path | [optional] 
**content** | **String** | Actual text of blog post | [optional] 
**created** | **Integer** | Seconds since 1970 | [optional] 
**modified** | **Integer** | Seconds since 1970 | [optional] 
**labels** | **Hash&lt;String, String&gt;** | Key/value pairs for metadata | [optional] 

## Code Sample

```ruby
require 'Anblog'

instance = Anblog::Post.new(path: null,
                                 content: null,
                                 created: null,
                                 modified: null,
                                 labels: null)
```



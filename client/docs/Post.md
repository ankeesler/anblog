# Anblog::Post

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**path** | **String** | Unique path | 
**content** | **String** | Actual text of blog post | 
**created** | **Integer** | Seconds since 1970 | 
**modified** | **Integer** | Seconds since 1970 | 
**labels** | **Hash&lt;String, String&gt;** | Key/value pairs for metadata | 

## Code Sample

```ruby
require 'Anblog'

instance = Anblog::Post.new(path: null,
                                 content: null,
                                 created: null,
                                 modified: null,
                                 labels: null)
```



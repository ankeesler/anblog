# Anblog::Post

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **Integer** |  | [optional] 
**title** | **String** |  | [optional] 
**content** | **String** |  | [optional] 
**created** | **DateTime** |  | [optional] 
**modified** | **DateTime** |  | [optional] 
**parent** | [**Post**](Post.md) |  | [optional] 
**children** | [**Array&lt;Post&gt;**](Post.md) |  | [optional] 

## Code Sample

```ruby
require 'Anblog'

instance = Anblog::Post.new(id: null,
                                 title: null,
                                 content: null,
                                 created: null,
                                 modified: null,
                                 parent: null,
                                 children: null)
```



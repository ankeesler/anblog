# Anblog::PostApi

All URIs are relative to *https://anblog.cfapps.io*

Method | HTTP request | Description
------------- | ------------- | -------------
[**add_post**](PostApi.md#add_post) | **POST** /posts | Add a new post
[**delete_post**](PostApi.md#delete_post) | **DELETE** /posts/{postPath} | Deletes a post
[**get_all_posts**](PostApi.md#get_all_posts) | **GET** /posts | Get all posts in ascending alphanumeric order of path
[**get_post_by_path**](PostApi.md#get_post_by_path) | **GET** /posts/{postPath} | Get post by path
[**patch_post**](PostApi.md#patch_post) | **PATCH** /posts/{postPath} | Patch an existing post
[**update_post**](PostApi.md#update_post) | **PUT** /posts/{postPath} | Update an existing post



## add_post

> Post add_post(post)

Add a new post

### Example

```ruby
# load the gem
require 'anblog'
# setup authorization
Anblog.configure do |config|
  # Configure HTTP basic authorization: blog_auth
  config.username = 'YOUR USERNAME'
  config.password = 'YOUR PASSWORD'
end

api_instance = Anblog::PostApi.new
post = Anblog::Post.new # Post | Post object to add

begin
  #Add a new post
  result = api_instance.add_post(post)
  p result
rescue Anblog::ApiError => e
  puts "Exception when calling PostApi->add_post: #{e}"
end
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **post** | [**Post**](Post.md)| Post object to add | 

### Return type

[**Post**](Post.md)

### Authorization

[blog_auth](../README.md#blog_auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## delete_post

> Post delete_post(post_path)

Deletes a post

### Example

```ruby
# load the gem
require 'anblog'
# setup authorization
Anblog.configure do |config|
  # Configure HTTP basic authorization: blog_auth
  config.username = 'YOUR USERNAME'
  config.password = 'YOUR PASSWORD'
end

api_instance = Anblog::PostApi.new
post_path = 'post_path_example' # String | The path of the post to delete

begin
  #Deletes a post
  result = api_instance.delete_post(post_path)
  p result
rescue Anblog::ApiError => e
  puts "Exception when calling PostApi->delete_post: #{e}"
end
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **post_path** | **String**| The path of the post to delete | 

### Return type

[**Post**](Post.md)

### Authorization

[blog_auth](../README.md#blog_auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## get_all_posts

> Array&lt;Post&gt; get_all_posts(opts)

Get all posts in ascending alphanumeric order of path

### Example

```ruby
# load the gem
require 'anblog'
# setup authorization
Anblog.configure do |config|
  # Configure HTTP basic authorization: blog_auth
  config.username = 'YOUR USERNAME'
  config.password = 'YOUR PASSWORD'
end

api_instance = Anblog::PostApi.new
opts = {
  prefix: 'prefix_example', # String | A path prefix used to filter returned posts
  fields: ['fields_example'] # Array<String> | The comma-separated list of fields to be returned in the response
}

begin
  #Get all posts in ascending alphanumeric order of path
  result = api_instance.get_all_posts(opts)
  p result
rescue Anblog::ApiError => e
  puts "Exception when calling PostApi->get_all_posts: #{e}"
end
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **prefix** | **String**| A path prefix used to filter returned posts | [optional] 
 **fields** | [**Array&lt;String&gt;**](String.md)| The comma-separated list of fields to be returned in the response | [optional] 

### Return type

[**Array&lt;Post&gt;**](Post.md)

### Authorization

[blog_auth](../README.md#blog_auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## get_post_by_path

> Post get_post_by_path(post_path, opts)

Get post by path

Returns a single post

### Example

```ruby
# load the gem
require 'anblog'
# setup authorization
Anblog.configure do |config|
  # Configure HTTP basic authorization: blog_auth
  config.username = 'YOUR USERNAME'
  config.password = 'YOUR PASSWORD'
end

api_instance = Anblog::PostApi.new
post_path = 'post_path_example' # String | The path of the post to return
opts = {
  fields: ['fields_example'] # Array<String> | The comma-separated list of fields to be returned in the response
}

begin
  #Get post by path
  result = api_instance.get_post_by_path(post_path, opts)
  p result
rescue Anblog::ApiError => e
  puts "Exception when calling PostApi->get_post_by_path: #{e}"
end
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **post_path** | **String**| The path of the post to return | 
 **fields** | [**Array&lt;String&gt;**](String.md)| The comma-separated list of fields to be returned in the response | [optional] 

### Return type

[**Post**](Post.md)

### Authorization

[blog_auth](../README.md#blog_auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## patch_post

> Post patch_post(post_path, post)

Patch an existing post

### Example

```ruby
# load the gem
require 'anblog'
# setup authorization
Anblog.configure do |config|
  # Configure HTTP basic authorization: blog_auth
  config.username = 'YOUR USERNAME'
  config.password = 'YOUR PASSWORD'
end

api_instance = Anblog::PostApi.new
post_path = 'post_path_example' # String | The path of the post to return
post = Anblog::Post.new # Post | Partial post object to merge into existing post

begin
  #Patch an existing post
  result = api_instance.patch_post(post_path, post)
  p result
rescue Anblog::ApiError => e
  puts "Exception when calling PostApi->patch_post: #{e}"
end
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **post_path** | **String**| The path of the post to return | 
 **post** | [**Post**](Post.md)| Partial post object to merge into existing post | 

### Return type

[**Post**](Post.md)

### Authorization

[blog_auth](../README.md#blog_auth)

### HTTP request headers

- **Content-Type**: application/merge-patch+json
- **Accept**: application/json


## update_post

> Post update_post(post_path, post)

Update an existing post

### Example

```ruby
# load the gem
require 'anblog'
# setup authorization
Anblog.configure do |config|
  # Configure HTTP basic authorization: blog_auth
  config.username = 'YOUR USERNAME'
  config.password = 'YOUR PASSWORD'
end

api_instance = Anblog::PostApi.new
post_path = 'post_path_example' # String | The path of the post to return
post = Anblog::Post.new # Post | Post object to update

begin
  #Update an existing post
  result = api_instance.update_post(post_path, post)
  p result
rescue Anblog::ApiError => e
  puts "Exception when calling PostApi->update_post: #{e}"
end
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **post_path** | **String**| The path of the post to return | 
 **post** | [**Post**](Post.md)| Post object to update | 

### Return type

[**Post**](Post.md)

### Authorization

[blog_auth](../README.md#blog_auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


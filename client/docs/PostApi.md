# Anblog::PostApi

All URIs are relative to *https://localhost:9090*

Method | HTTP request | Description
------------- | ------------- | -------------
[**add_post**](PostApi.md#add_post) | **POST** /posts | Add a new post
[**delete_post**](PostApi.md#delete_post) | **DELETE** /posts/{postId} | Deletes a post
[**get_all_posts**](PostApi.md#get_all_posts) | **GET** /posts | Get all posts
[**get_post_by_id**](PostApi.md#get_post_by_id) | **GET** /posts/{postId} | Get post by ID
[**update_post**](PostApi.md#update_post) | **PUT** /posts/{postId} | Update an existing post



## add_post

> Post add_post(post)

Add a new post

### Example

```ruby
# load the gem
require 'anblog'

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

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## delete_post

> Post delete_post(post_id)

Deletes a post

### Example

```ruby
# load the gem
require 'anblog'

api_instance = Anblog::PostApi.new
post_id = 56 # Integer | Post id to delete

begin
  #Deletes a post
  result = api_instance.delete_post(post_id)
  p result
rescue Anblog::ApiError => e
  puts "Exception when calling PostApi->delete_post: #{e}"
end
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **post_id** | **Integer**| Post id to delete | 

### Return type

[**Post**](Post.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## get_all_posts

> Array&lt;Post&gt; get_all_posts

Get all posts

### Example

```ruby
# load the gem
require 'anblog'

api_instance = Anblog::PostApi.new

begin
  #Get all posts
  result = api_instance.get_all_posts
  p result
rescue Anblog::ApiError => e
  puts "Exception when calling PostApi->get_all_posts: #{e}"
end
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**Array&lt;Post&gt;**](Post.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## get_post_by_id

> Post get_post_by_id(post_id)

Get post by ID

Returns a single post

### Example

```ruby
# load the gem
require 'anblog'

api_instance = Anblog::PostApi.new
post_id = 56 # Integer | ID of post to return

begin
  #Get post by ID
  result = api_instance.get_post_by_id(post_id)
  p result
rescue Anblog::ApiError => e
  puts "Exception when calling PostApi->get_post_by_id: #{e}"
end
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **post_id** | **Integer**| ID of post to return | 

### Return type

[**Post**](Post.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## update_post

> Post update_post(post_id, post)

Update an existing post

### Example

```ruby
# load the gem
require 'anblog'
# setup authorization
Anblog.configure do |config|
  # Configure OAuth2 access token for authorization: blog_auth
  config.access_token = 'YOUR ACCESS TOKEN'
end

api_instance = Anblog::PostApi.new
post_id = 56 # Integer | ID of post to return
post = Anblog::Post.new # Post | Post object to update

begin
  #Update an existing post
  result = api_instance.update_post(post_id, post)
  p result
rescue Anblog::ApiError => e
  puts "Exception when calling PostApi->update_post: #{e}"
end
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **post_id** | **Integer**| ID of post to return | 
 **post** | [**Post**](Post.md)| Post object to update | 

### Return type

[**Post**](Post.md)

### Authorization

[blog_auth](../README.md#blog_auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


=begin
#anblog

#Its a *very* simple blog API. Code is [here](https://github.com/ankeesler/anblog).

The version of the OpenAPI document: 1.0.0

Generated by: https://openapi-generator.tech
OpenAPI Generator version: 4.0.0

=end

require 'uri'

module Anblog
  class PostApi
    attr_accessor :api_client

    def initialize(api_client = ApiClient.default)
      @api_client = api_client
    end
    # Add a new post
    # @param post [Post] Post object to add
    # @param [Hash] opts the optional parameters
    # @return [Post]
    def add_post(post, opts = {})
      data, _status_code, _headers = add_post_with_http_info(post, opts)
      data
    end

    # Add a new post
    # @param post [Post] Post object to add
    # @param [Hash] opts the optional parameters
    # @return [Array<(Post, Integer, Hash)>] Post data, response status code and response headers
    def add_post_with_http_info(post, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: PostApi.add_post ...'
      end
      # verify the required parameter 'post' is set
      if @api_client.config.client_side_validation && post.nil?
        fail ArgumentError, "Missing the required parameter 'post' when calling PostApi.add_post"
      end
      # resource path
      local_var_path = '/posts'

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['application/json'])
      # HTTP header 'Content-Type'
      header_params['Content-Type'] = @api_client.select_header_content_type(['application/json'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] || @api_client.object_to_http_body(post) 

      # return_type
      return_type = opts[:return_type] || 'Post' 

      # auth_names
      auth_names = opts[:auth_names] || []

      new_options = opts.merge(
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:POST, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: PostApi#add_post\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # Deletes a post
    # @param post_id [Integer] Post id to delete
    # @param [Hash] opts the optional parameters
    # @return [Post]
    def delete_post(post_id, opts = {})
      data, _status_code, _headers = delete_post_with_http_info(post_id, opts)
      data
    end

    # Deletes a post
    # @param post_id [Integer] Post id to delete
    # @param [Hash] opts the optional parameters
    # @return [Array<(Post, Integer, Hash)>] Post data, response status code and response headers
    def delete_post_with_http_info(post_id, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: PostApi.delete_post ...'
      end
      # verify the required parameter 'post_id' is set
      if @api_client.config.client_side_validation && post_id.nil?
        fail ArgumentError, "Missing the required parameter 'post_id' when calling PostApi.delete_post"
      end
      # resource path
      local_var_path = '/posts/{postId}'.sub('{' + 'postId' + '}', post_id.to_s)

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['application/json'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] 

      # return_type
      return_type = opts[:return_type] || 'Post' 

      # auth_names
      auth_names = opts[:auth_names] || []

      new_options = opts.merge(
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:DELETE, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: PostApi#delete_post\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # Get all posts
    # @param [Hash] opts the optional parameters
    # @return [Array<Post>]
    def get_all_posts(opts = {})
      data, _status_code, _headers = get_all_posts_with_http_info(opts)
      data
    end

    # Get all posts
    # @param [Hash] opts the optional parameters
    # @return [Array<(Array<Post>, Integer, Hash)>] Array<Post> data, response status code and response headers
    def get_all_posts_with_http_info(opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: PostApi.get_all_posts ...'
      end
      # resource path
      local_var_path = '/posts'

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['application/json'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] 

      # return_type
      return_type = opts[:return_type] || 'Array<Post>' 

      # auth_names
      auth_names = opts[:auth_names] || []

      new_options = opts.merge(
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:GET, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: PostApi#get_all_posts\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # Get post by ID
    # Returns a single post
    # @param post_id [Integer] ID of post to return
    # @param [Hash] opts the optional parameters
    # @return [Post]
    def get_post_by_id(post_id, opts = {})
      data, _status_code, _headers = get_post_by_id_with_http_info(post_id, opts)
      data
    end

    # Get post by ID
    # Returns a single post
    # @param post_id [Integer] ID of post to return
    # @param [Hash] opts the optional parameters
    # @return [Array<(Post, Integer, Hash)>] Post data, response status code and response headers
    def get_post_by_id_with_http_info(post_id, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: PostApi.get_post_by_id ...'
      end
      # verify the required parameter 'post_id' is set
      if @api_client.config.client_side_validation && post_id.nil?
        fail ArgumentError, "Missing the required parameter 'post_id' when calling PostApi.get_post_by_id"
      end
      # resource path
      local_var_path = '/posts/{postId}'.sub('{' + 'postId' + '}', post_id.to_s)

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['application/json'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] 

      # return_type
      return_type = opts[:return_type] || 'Post' 

      # auth_names
      auth_names = opts[:auth_names] || []

      new_options = opts.merge(
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:GET, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: PostApi#get_post_by_id\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # Update an existing post
    # @param post_id [Integer] ID of post to return
    # @param post [Post] Post object to update
    # @param [Hash] opts the optional parameters
    # @return [Post]
    def update_post(post_id, post, opts = {})
      data, _status_code, _headers = update_post_with_http_info(post_id, post, opts)
      data
    end

    # Update an existing post
    # @param post_id [Integer] ID of post to return
    # @param post [Post] Post object to update
    # @param [Hash] opts the optional parameters
    # @return [Array<(Post, Integer, Hash)>] Post data, response status code and response headers
    def update_post_with_http_info(post_id, post, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: PostApi.update_post ...'
      end
      # verify the required parameter 'post_id' is set
      if @api_client.config.client_side_validation && post_id.nil?
        fail ArgumentError, "Missing the required parameter 'post_id' when calling PostApi.update_post"
      end
      # verify the required parameter 'post' is set
      if @api_client.config.client_side_validation && post.nil?
        fail ArgumentError, "Missing the required parameter 'post' when calling PostApi.update_post"
      end
      # resource path
      local_var_path = '/posts/{postId}'.sub('{' + 'postId' + '}', post_id.to_s)

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['application/json'])
      # HTTP header 'Content-Type'
      header_params['Content-Type'] = @api_client.select_header_content_type(['application/json'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] || @api_client.object_to_http_body(post) 

      # return_type
      return_type = opts[:return_type] || 'Post' 

      # auth_names
      auth_names = opts[:auth_names] || ['blog_auth']

      new_options = opts.merge(
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type
      )

      data, status_code, headers = @api_client.call_api(:PUT, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: PostApi#update_post\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end
  end
end
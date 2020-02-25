=begin
#anblog

#It's a *very* simple blog API. Code is [here](https://github.com/ankeesler/anblog).

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
      auth_names = opts[:auth_names] || ['blog_auth']

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
    # @param post_path [String] The path of the post to delete
    # @param [Hash] opts the optional parameters
    # @return [Post]
    def delete_post(post_path, opts = {})
      data, _status_code, _headers = delete_post_with_http_info(post_path, opts)
      data
    end

    # Deletes a post
    # @param post_path [String] The path of the post to delete
    # @param [Hash] opts the optional parameters
    # @return [Array<(Post, Integer, Hash)>] Post data, response status code and response headers
    def delete_post_with_http_info(post_path, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: PostApi.delete_post ...'
      end
      # verify the required parameter 'post_path' is set
      if @api_client.config.client_side_validation && post_path.nil?
        fail ArgumentError, "Missing the required parameter 'post_path' when calling PostApi.delete_post"
      end
      # resource path
      local_var_path = '/posts/{postPath}'.sub('{' + 'postPath' + '}', post_path.to_s)

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
      auth_names = opts[:auth_names] || ['blog_auth']

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

    # Get all posts in ascending alphanumeric order of path
    # @param [Hash] opts the optional parameters
    # @option opts [String] :prefix A path prefix used to filter returned posts
    # @option opts [Array<String>] :fields The comma-separated list of fields to be returned in the response
    # @return [Array<Post>]
    def get_all_posts(opts = {})
      data, _status_code, _headers = get_all_posts_with_http_info(opts)
      data
    end

    # Get all posts in ascending alphanumeric order of path
    # @param [Hash] opts the optional parameters
    # @option opts [String] :prefix A path prefix used to filter returned posts
    # @option opts [Array<String>] :fields The comma-separated list of fields to be returned in the response
    # @return [Array<(Array<Post>, Integer, Hash)>] Array<Post> data, response status code and response headers
    def get_all_posts_with_http_info(opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: PostApi.get_all_posts ...'
      end
      # resource path
      local_var_path = '/posts'

      # query parameters
      query_params = opts[:query_params] || {}
      query_params[:'prefix'] = opts[:'prefix'] if !opts[:'prefix'].nil?
      query_params[:'$fields'] = @api_client.build_collection_param(opts[:'fields'], :multi) if !opts[:'fields'].nil?

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
      auth_names = opts[:auth_names] || ['blog_auth']

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

    # Get post by path
    # Returns a single post
    # @param post_path [String] The path of the post to return
    # @param [Hash] opts the optional parameters
    # @option opts [Array<String>] :fields The comma-separated list of fields to be returned in the response
    # @return [Post]
    def get_post_by_path(post_path, opts = {})
      data, _status_code, _headers = get_post_by_path_with_http_info(post_path, opts)
      data
    end

    # Get post by path
    # Returns a single post
    # @param post_path [String] The path of the post to return
    # @param [Hash] opts the optional parameters
    # @option opts [Array<String>] :fields The comma-separated list of fields to be returned in the response
    # @return [Array<(Post, Integer, Hash)>] Post data, response status code and response headers
    def get_post_by_path_with_http_info(post_path, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: PostApi.get_post_by_path ...'
      end
      # verify the required parameter 'post_path' is set
      if @api_client.config.client_side_validation && post_path.nil?
        fail ArgumentError, "Missing the required parameter 'post_path' when calling PostApi.get_post_by_path"
      end
      # resource path
      local_var_path = '/posts/{postPath}'.sub('{' + 'postPath' + '}', post_path.to_s)

      # query parameters
      query_params = opts[:query_params] || {}
      query_params[:'$fields'] = @api_client.build_collection_param(opts[:'fields'], :multi) if !opts[:'fields'].nil?

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
      auth_names = opts[:auth_names] || ['blog_auth']

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
        @api_client.config.logger.debug "API called: PostApi#get_post_by_path\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # Patch an existing post
    # @param post_path [String] The path of the post to return
    # @param body [String] Partial post object to merge into existing post
    # @param [Hash] opts the optional parameters
    # @return [Post]
    def patch_post(post_path, body, opts = {})
      data, _status_code, _headers = patch_post_with_http_info(post_path, body, opts)
      data
    end

    # Patch an existing post
    # @param post_path [String] The path of the post to return
    # @param body [String] Partial post object to merge into existing post
    # @param [Hash] opts the optional parameters
    # @return [Array<(Post, Integer, Hash)>] Post data, response status code and response headers
    def patch_post_with_http_info(post_path, body, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: PostApi.patch_post ...'
      end
      # verify the required parameter 'post_path' is set
      if @api_client.config.client_side_validation && post_path.nil?
        fail ArgumentError, "Missing the required parameter 'post_path' when calling PostApi.patch_post"
      end
      # verify the required parameter 'body' is set
      if @api_client.config.client_side_validation && body.nil?
        fail ArgumentError, "Missing the required parameter 'body' when calling PostApi.patch_post"
      end
      # resource path
      local_var_path = '/posts/{postPath}'.sub('{' + 'postPath' + '}', post_path.to_s)

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['application/json'])
      # HTTP header 'Content-Type'
      header_params['Content-Type'] = @api_client.select_header_content_type(['application/merge-patch+json'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] || @api_client.object_to_http_body(body) 

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

      data, status_code, headers = @api_client.call_api(:PATCH, local_var_path, new_options)
      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: PostApi#patch_post\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end

    # Update an existing post
    # @param post_path [String] The path of the post to return
    # @param post [Post] Post object to update
    # @param [Hash] opts the optional parameters
    # @return [Post]
    def update_post(post_path, post, opts = {})
      data, _status_code, _headers = update_post_with_http_info(post_path, post, opts)
      data
    end

    # Update an existing post
    # @param post_path [String] The path of the post to return
    # @param post [Post] Post object to update
    # @param [Hash] opts the optional parameters
    # @return [Array<(Post, Integer, Hash)>] Post data, response status code and response headers
    def update_post_with_http_info(post_path, post, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: PostApi.update_post ...'
      end
      # verify the required parameter 'post_path' is set
      if @api_client.config.client_side_validation && post_path.nil?
        fail ArgumentError, "Missing the required parameter 'post_path' when calling PostApi.update_post"
      end
      # verify the required parameter 'post' is set
      if @api_client.config.client_side_validation && post.nil?
        fail ArgumentError, "Missing the required parameter 'post' when calling PostApi.update_post"
      end
      # resource path
      local_var_path = '/posts/{postPath}'.sub('{' + 'postPath' + '}', post_path.to_s)

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

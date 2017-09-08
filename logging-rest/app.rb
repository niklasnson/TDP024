require 'sinatra'
require 'sinatra/activerecord'
require 'json'

set :port, 8060
set :database, {adapter: "sqlite3", database: "logging.sqlite3"}

require "./models"

get '/list' do
  @users = User.all
  @users.to_json
end

get %r{/find.(\d+)} do |key|
  @users = User.find_by(id: key)
  @users.nil? ? 'null' : @users.to_json
end

get %r{/find.([A-Za-z ]+)} do |pattern|
  @users = User.where(name: pattern)
  @users.empty? ? 'null' : @users.to_json
end



require 'sinatra'
require 'sinatra/activerecord'
require 'json'

set :port, 8060
set :database, {adapter: "sqlite3", database: "persons.sqlite3"}

require "./models"

get '/list' do
  @users = User.all
  @users.to_json
end

get %r{/find.(\d+)} do |key|
  @users = User.find_by(id: key)
  @user = @users.nil? ? 'null' : @users.to_json
  [200, @user]
end

get %r{/find.([A-Za-z+]+)} do |pattern|
  pattern.gsub!(/\+/, " ")
  @users = User.where(name: pattern)
  @user = @users.empty? ? 'null' : @users.to_json
  [200, @user]
end



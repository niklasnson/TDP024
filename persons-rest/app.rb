require 'sinatra'
require 'sinatra/activerecord'

set :port, 8060
set :database, {adapter: "sqlite3", database: "persons.sqlite3"}

require "./models"

get '/list' do
  'Hello world'
end

get %r{/find.(\w+)} do |name|
  "-> #{name}"
end

get %r{/find.(\d+)} do |key|
  "-> #{key}"
end

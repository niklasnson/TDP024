require 'sinatra'
require 'sinatra/activerecord'
require 'json'

set :port, 8070
set :database, {adapter: "sqlite3", database: "banks.sqlite3"}

require "./models"

get '/list' do
  @banks = Bank.all
  @banks.to_json
end

get %r{/find.(\d+)} do |key|
  @banks = Bank.find_by(id: key)
  @banks.nil? ? 'null' : @banks.to_json
end

get %r{/find.([A-Za-z ]+)} do |pattern|
  @banks = Bank.where(name: pattern)
  @banks.empty? ? 'null' : @banks.to_json
end



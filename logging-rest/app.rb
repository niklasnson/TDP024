require 'sinatra'
require 'sinatra/activerecord'
require './environments'
require 'json'

set :environment, :development
set :port, 8090

require "./models"

post '/log' do
  payload = JSON.parse request.body.read
  @log = Log.new(
    severity: payload["severity"],
    short_desc: payload["short_desc"],
    long_desc: payload["long_desc"],
    timestamp: payload["timestamp"]
  )
  if @log.save
    [200,'OK']
  else
    [406, 'Not Acceptable']
  end
end

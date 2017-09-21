require 'sinatra'
require 'sinatra/activerecord'
require './environments'
require 'json'

set :environment, :development
set :port, 8090

require "./models"

post '/log' do

  data = JSON.parse request.body.read
  @log = Log.new(
    level: params[:level],
    short_desc: params[:short_desc],
    long_desc: params[:long_desc],
    timestamp: params[:timestamp]
  )
  if @log.save
    [200,'OK']
  else
    [406, 'Not Acceptable']
  end
end

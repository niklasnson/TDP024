require 'sinatra'
require 'sinatra/activerecord'
require './environments'
require 'json'

set :environment, :development
set :port, 8060

require "./models"

post '/log' do

  data = JSON.parse request.body.read
  @log_item = LogItem.new(
    log_id: 1,
    level: params[:level],
    short_desc: params[:short_desc],
    long_desc: params[:long_desc],
    timestamp: params[:timestamp]
  )
  if @log_item.save
    [200,'OK']
  else
    [406, 'Not Acceptable']
  end
end

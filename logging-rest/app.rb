require 'sinatra'
require 'sinatra/activerecord'
require './environments'
require 'json'

set :environment, :development
set :port, 8060

require "./models"

get '/:level/:message' do
  @log_item = LogItem.new(level: params[:level],  message: params[:message])
  if @log_item.save
    [200,'OK']
  else
    [406, 'Not Acceptable']
  end
end

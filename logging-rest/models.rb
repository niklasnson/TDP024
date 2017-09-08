class Log < ActiveRecord::Base
  has_many :log_items
end

class LogItem < ActiveRecord::Base
  belongs_to :log
end

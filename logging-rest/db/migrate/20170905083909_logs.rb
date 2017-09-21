class Logs < ActiveRecord::Migration[5.1]
  def change
  	create_table :logs do |t|
      t.integer :level
      t.string :short_desc
      t.string :long_desc
      t.string :timestamp
  	end
  end
end

class CreateLogItems < ActiveRecord::Migration[5.1]
  def change
    create_table :log_items do |t|
      t.integer :log_id
      t.integer :level
      t.string :short_desc
      t.string :long_desc
      t.string :timestamp
  	end
  end
end

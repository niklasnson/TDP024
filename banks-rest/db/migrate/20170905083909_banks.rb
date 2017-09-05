class Banks < ActiveRecord::Migration[5.1]
  def change
  	create_table :banks do |t|
  		t.string :name
  	end
  end
end

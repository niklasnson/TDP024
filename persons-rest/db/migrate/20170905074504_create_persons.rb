class CreatePersons < ActiveRecord::Migration[5.1]
  def change
  	create_table :persons do |t|
  		t.string :name
  	end
  end
end

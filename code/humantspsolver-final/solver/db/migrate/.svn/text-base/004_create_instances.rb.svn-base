class CreateInstances < ActiveRecord::Migration
  def self.up
    create_table :instances do |t|
      t.references :problem
      t.datetime :start_date

      t.timestamps
    end
  end

  def self.down
    drop_table :instances
  end
end

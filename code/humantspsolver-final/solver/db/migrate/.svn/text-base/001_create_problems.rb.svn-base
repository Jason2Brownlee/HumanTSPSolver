class CreateProblems < ActiveRecord::Migration
  def self.up
    create_table :problems do |t|
      t.string :name
      t.string :edge_weight_type
      t.text :description
      t.integer :num_cities

      t.timestamps
    end
  end

  def self.down
    drop_table :problems
  end
end

class CreateOptimalTours < ActiveRecord::Migration
  def self.up
    create_table :optimal_tours do |t|
      t.references :problem
      t.integer :city_from
      t.integer :city_to

      t.timestamps
    end
  end

  def self.down
    drop_table :optimal_tours
  end
end

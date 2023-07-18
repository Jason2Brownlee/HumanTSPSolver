class CreateCoordinates < ActiveRecord::Migration
  def self.up
    create_table :coordinates do |t|
      t.references :problem
      t.integer :city
      t.decimal :x, :precision => 10, :scale => 2
      t.decimal :y, :precision => 10, :scale => 2

      t.timestamps
    end
  end

  def self.down
    drop_table :coordinates
  end
end

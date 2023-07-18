class CreateHistories < ActiveRecord::Migration
  def self.up
    create_table :histories do |t|
      t.references :instance
      t.integer :city_from
      t.integer :city_to
      t.integer :count

      t.timestamps
    end
  end

  def self.down
    drop_table :histories
  end
end

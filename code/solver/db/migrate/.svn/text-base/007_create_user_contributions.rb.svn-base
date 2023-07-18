class CreateUserContributions < ActiveRecord::Migration
  def self.up
    create_table :user_contributions do |t|
      t.references :user
      t.references :instance
      t.integer :count

      t.timestamps
    end
  end

  def self.down
    drop_table :user_contributions
  end
end

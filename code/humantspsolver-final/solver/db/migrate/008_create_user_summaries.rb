class CreateUserSummaries < ActiveRecord::Migration
  def self.up
    create_table :user_summaries do |t|
      t.string :login
      t.integer :num_instances
      t.integer :num_contributions

      t.timestamps
    end
  end

  def self.down
    drop_table :user_summaries
  end
end

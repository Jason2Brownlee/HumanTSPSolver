class Instance < ActiveRecord::Base
  belongs_to :problem
  has_many :user_contributions
  has_many :histories
    
  def self.current_instance
    return Instance.find(:first, :order => "start_date DESC")
  end
  
end

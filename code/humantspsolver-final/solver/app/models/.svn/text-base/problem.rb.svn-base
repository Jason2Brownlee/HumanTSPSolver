class Problem < ActiveRecord::Base
  
  has_many :coordinates
  has_many :instances
  has_many :optimal_tours
  has_many :optimal_tour_summaries
  
  
  #
  # does the problem have an optimal tour: help method 
  #
  def has_optima?
    return optimal_tours.count > 0
  end
  
  
  #
  # problem difficulty, for visualisation and such
  #
  def difficulty
    if num_cities < 100
      return "easy"
    elsif num_cities >= 100 && num_cities < 1000
      return "moderate"
    elsif num_cities >= 1000 && num_cities < 10000
      return "hard"
    elsif num_cities >= 10000
      return "insane"
    end
    
    return "Unknown"
  end
  
end

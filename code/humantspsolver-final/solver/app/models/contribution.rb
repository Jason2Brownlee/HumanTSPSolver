class Contribution
   include Validatable
   # see: http://validatable.rubyforge.org/
   
   attr_accessor :from_city, :to_city
  
   # both required
   validates_presence_of :from_city, :to_city, 
      :message => "Citites Required."
   # must be numbers
   validates_numericality_of :from_city, :to_city, 
      :message => "Cities are not numbers."
   # must be different numbers
    validates_true_for :from_city, :to_city, :logic => lambda { from_city != to_city },
      :message => "Cities are the same."

  
  
  #
  # save an instance
  #
  def save(instance, problem)
    # validate    
    if(! valid?)
      return false
    end
    
    a = Integer(@from_city)
    b = Integer (@to_city)
    
    # ensure within bounds of the probelm
    # should use a stored procedure and check them against the coord list
    if(a < 1 || a > problem.num_cities)    
      return false
    end
    if(b < 1 || b > problem.num_cities)
      return false
    end    
    
    # swap
    if(a > b)
      tmp = a
      a = b
      b = tmp
    end
    
    #
    # maybe use "Dynamic attribute-based finders" in the future
    #
    
    # attempt a retrival      
    history = History.find(:first, :conditions => ["city_from=? AND city_to=? AND instance_id=?", a, b, instance.id])
          
    if(history == nil)      
      # build a new record
      history = History.new
      history.instance_id = instance.id
      history.city_from = a
      history.city_to = b
      history.count = 1
      return history.save
    else
      #increment and save the record
      return history.increment!('count')
    end
  end
    
  
  
end

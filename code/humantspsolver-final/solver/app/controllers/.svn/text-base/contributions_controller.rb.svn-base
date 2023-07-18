class ContributionsController < ApplicationController
  
  before_filter :load_current_problem_instance
  
  #
  # prep
  #
  def load_current_problem_instance
    # grab the most recent instance
    @instance = Instance.current_instance   
  end
  
  
  def prepare_subproblem
    num_cities = user_num_cities()
    problem = @instance.problem    
    
    # select a random starting position
    startCoord = problem.coordinates.find(:first, :order => 'RAND()')
    # select a subset including the starting position (distance zero)
    @sub_problem = Coordinate.find_by_sql ["SELECT c.* FROM coordinates c WHERE problem_id=? ORDER BY ((c.x-?)*(c.x-?) + (c.y-?)*(c.y-?)) ASC LIMIT ?", problem.id, startCoord.x, startCoord.x, startCoord.y, startCoord.y, num_cities]
    
    # seed some contributions
    @contributions = Array.new(num_cities)
    num_cities.times { |i|  @contributions[i] = Contribution.new }
  end  
  
  def user_num_cities
    num_s = params[:cities]
    max = @instance.problem.num_cities
    
    if(num_s == nil)
      params[:cities] = "easy"
      num = 10
    elsif(num_s.index("easy"))
      num = 10
    elsif(num_s.index("moderate"))
      num = 20
    elsif(num_s.index("hard"))
      num = 30
    elsif(num_s.index("all"))
      num = max
    else
      params[:cities] = "easy"
      num = 10
    end
    
    # check for too much - could happen for small instances
    if(num > max)
      num = max
    end
    
    return num;
  end
  
  
  
  def easy
   default("easy")
  end
  def moderate
    default("moderate")
  end
  def hard
    default("hard")
  end
  def all
    default("all")
  end
  
  def default(num_cities)
    params[:cities] = num_cities
    @onload = 'setAppletSize()'
    prepare_subproblem
    render :action => 'show'
  end
  
  #
  # default to small
  #
  def index
    redirect_to :action => 'easy'
  end
  
  #
  # renader sub problems
  #
  def show        
    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @sub_problem }
    end    
  end  
    
    
  def contribute
#    # update any size change
#    psize = params[:applet][:size]
#    if(psize != nil)
#      current = session[:applet_size]    
#      if(current == nil || current != psize)
#        session[:applet_size] = psize
#      end
#    end
    
    if request.post?
      # count recorded contributions
      saves = 0
      
      # process all contributions 
      params[:contributions].each_value do |contribution|
        c = Contribution.new
        c.from_city = contribution['from_city']
        c.to_city = contribution['to_city']
        if(c.save(@instance, @instance.problem))
          saves += 1
        end
      end
      
      if(saves != 0)
        update_user_contributions(saves)
        #flash[:message] = "Saved your contribution with <u>#{saves}</u> edges."   
      end
    end
    
    # index on empty, otherwise, where they came from
    redirect_to :action => params[:cities]
  end 
  
  
  def update_user_contributions(num_contributions)
    if(session[:user]!= nil)
      user = session[:user]
      contribution = user.user_contributions.find(:first, :conditions => ["instance_id=? and user_id=?",@instance.id, user.id])
      if(contribution == nil)
        contribution = user.user_contributions.build()
        contribution.user_id = user.id
        contribution.instance_id = @instance.id
        contribution.count = num_contributions
        # throws an exception if bad things happen
        contribution.save!
      else
        # throws an exception if bad things happen
        contribution.update_attributes(:count => (contribution.count + num_contributions))
      end    
    end
  end
  
  
end

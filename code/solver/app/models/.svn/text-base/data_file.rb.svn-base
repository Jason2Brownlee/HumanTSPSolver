class DataFile 
  
  def self.saveProblemOptimalEdge(problem, from, to)
    # swap as needed
    if(from > to)
      tmp = from
      from = to
      to = tmp
    end
    
    # connect back to first edge
    edge = problem.optimal_tours.build
    edge.city_from = from
    edge.city_to = to
    edge.save!
  end
  
  def self.saveOptima(upload, problem)    
    # parse the lines
    lines = upload['datafile'].readlines   
    processingCoords = false
    problemMap = {}
    firstCoord = -1
    currentCoord = -1
    
    # process the lines    
    for line in lines
      # try and parse the line
      cf = line.index(':')
      
      # check for processing meta data
      if(!processingCoords && cf != nil)
        # capture the bits
        key,value = line.split(":")
        problemMap[key.strip] = value.strip      
        
      # check for processing coordinates
      elsif(processingCoords)
        # check for EOF
        if(line.index("EOF") != nil)         
          break
        # check for last coordinate
        elsif(line.index("-1") != nil)
          # connect back to first edge
          saveProblemOptimalEdge(problem, Integer(currentCoord), Integer(firstCoord))
          break
        # check for first case
        elsif(firstCoord == -1)
          currentCoord = line
          firstCoord = line
        # normal case
        else
          # normal case
          saveProblemOptimalEdge(problem, Integer(currentCoord), Integer(line))
          # update last coord
          currentCoord = line                       
        end        
      # assume it is time to transition from metadata to coordinates
      elsif(line.index("TOUR_SECTION") != nil)
        # ensure the number of cities and name match
        if(Integer(problemMap['DIMENSION']) != problem.num_cities || 
          problemMap['NAME'].index(problem.name) == nil)
          return "Tour name #{problemMap['NAME']} or size #{problemMap['DIMENSION']} does not match expected name=#{problem.name}, size=#{problem.num_cities}"       
        else
          # begin recording edges
          processingCoords = true  
        end
      end  
    end 
    
    return nil
  end  
  
  def self.saveProblem(upload)
    # parse the lines
    lines = upload['datafile'].readlines   
    problem = Problem.new        
    processingCoords = false
    problemMap = {}
    
    # process the lines    
    for line in lines
      # parse the line by default
      cf = line.index(':')
      
      # check for head
      if(!processingCoords && cf != nil)
        # capture the bits
        key,value = line.split(":")
        problemMap[key.strip] = value.strip      
      # check for processing cordinates
      elsif(processingCoords)
        # check for EOF
        if line.index("EOF") != nil        
           break
        else
          city,x,y = line.split
          # save the coordinate
          coord = problem.coordinates.build
          coord.city = city
          coord.x = Float(x)
          coord.y = Float(y)
          coord.save!
        end
      # check for transition from head into data
      else
        processingCoords = true       
        
        # save the problem        
        problem.name = problemMap['NAME']
        problem.description = problemMap['COMMENT']
        problem.edge_weight_type = problemMap['EDGE_WEIGHT_TYPE']
        problem.num_cities = problemMap['DIMENSION']
        problem.save!
      end  
    end  
    
    return nil
  end  
  
end

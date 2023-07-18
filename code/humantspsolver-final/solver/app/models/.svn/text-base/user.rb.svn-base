require 'digest/sha1'

class User < ActiveRecord::Base
  
  has_many :user_contributions
  
  # validation of parameter
  validates_length_of :login, :within => 3..25
  validates_length_of :password, :within => 5..40
  validates_presence_of :login, :email, :password, :password_confirmation, :salt
  validates_uniqueness_of :login, :email
  validates_confirmation_of :password
  validates_format_of :email, :with => /^([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})$/i, :message => "Invalid email"  
  
  # Attributes named in this macro are protected from mass-assignment
  attr_protected :id, :salt
  
  attr_accessor :password, :password_confirmation
  
  
  
  def current_score
    num = user_contributions.sum(:count)
    if(num == nil)
      return 0
    end
    return num
  end
  
  def experience_level(score = nil)
    if(score == nil)
      score = current_score
    end
    return User.calc_experience_level(score)
  end
  
  def self.calc_experience_level(num)
    if(num < 1000)
      return "beginner"
    elsif(num>=1000 && num<10000)
      return "intermediate"
    elsif(num>=10000 && num<100000)
      return "advanced"
    elsif(num>=100000 && num<1000000)
      return "master"
    elsif(num>=1000000)
      return "godlike"
    end    
    return "unknown"
  end
  
  def self.users_best_ever(limit = 15)
    return User.find_by_sql ["SELECT u.id as id, u.login as login, count(c.id) as num_instances, sum(c.count) as num_contributions " + 
      "FROM users u, user_contributions c " +
      "WHERE u.id = c.user_id GROUP BY u.id ORDER BY num_contributions DESC LIMIT ?", limit]
  end
  
  def self.users_best_for_instance(instance, limit = 15)     
    return User.find_by_sql ["SELECT u.id as id, u.login as login, sum(c.count) as num_contributions " + 
      "FROM users u, user_contributions c " +
      "WHERE u.id = c.user_id AND c.instance_id = ? " +
      "GROUP BY u.id ORDER BY num_contributions DESC LIMIT ?", instance.id, limit]
  end
  
  def self.users_best_for_current_instance(limit = 15)
    instance = Instance.current_instance
    return users_best_for_instance(instance, limit)
  end
  
  def self.users_recent_contributors(limit = 15)    
    # top n, ordered by time desc    
    return User.find_by_sql [
      "SELECT u.id as id, u.login as login, count(c.id) as num_instances, sum(c.count) as num_contributions, max(c.updated_at) as last_updated " + 
      "FROM users u, user_contributions c " +
      "WHERE u.id = c.user_id " + 
      "GROUP BY u.id " +
      "ORDER BY last_updated DESC " +
      "LIMIT ?", limit]
  end
  
  
  # make sure that the password that is stored in the database is encrypted
  # This method gets called whenever a password is assigned to a user
  def password=(pass)
    @password=pass
    self.salt = User.random_string(10) if !self.salt?
    self.hashed_password = User.encrypt(@password, self.salt)
  end 
  
  
  # The class method authenticate returns a user if they’re 
  # hashed password matches the one stored for that user in the database.
  # First we find the user that corresponds to the login. 
  # If we didn’t find the login authentication fails. 
  # We then compute the users hashed password using the supplied password and the users salt. 
  # Authentication is successful if these values match.
  def self.authenticate(login, pass)
    u=find(:first, :conditions=>["login = ?", login])
    return nil if u.nil?
    return u if User.encrypt(pass, u.salt)==u.hashed_password
    nil
  end  
  
  # generate a new random password and send to the user
  def send_new_password
    new_pass = User.random_string(10)
    self.password = self.password_confirmation = new_pass
    self.save
    Notifications.deliver_forgot_password(self.email, self.login, new_pass)
  end
  
  
  
  
  #
  # protected
  #
  protected
  
  # We set the salt for the user to a random string if it hasn’t already been set
  # This happens the first time the users password is set.
  def self.random_string(len)
    #generate a random password consisting of strings and digits
    chars = ("a".."z").to_a + ("A".."Z").to_a + ("0".."9").to_a
    newpass = ""
    1.upto(len) { |i| newpass << chars[rand(chars.size-1)] }
    return newpass
  end 
  
  # encrypt using the salt and password
  def self.encrypt(pass, salt)
    Digest::SHA1.hexdigest(pass+salt)
  end    
  
end

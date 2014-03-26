package worms.model.Objects;
 
/**
 * A class of worms involving a position consisting of an X- and Y-coordinate, an angle,
 * a radius, a mass, action points (current and maximum)
 *
 * @version 1.0
 * @Author Ruben Schroyen & Ralph Vancampenhoudt
 */
 
import java.lang.Math;

import worms.util.Util;
 
public class Worm 
{                    
       
        /**
         * We start off declaring every single used parameter in the class Worm:
         *
         * @param pos_x
         *              The X-coordinate for the worm in meters
         *
         * @param pos_y
         *              The Y-coordinate for the worm in meters
         *
         * @param angle
         *              The angle the worm is facing in radial
         *
         * @param radius
         *              The radius of the worm in meters
         *
         * @param current_AP
         *              The current amount of action points the worm has left
         *
         * @param max_AP
         *              The maximum amount of action points the worm has
         *
         * @param name
         *              The name a worm has been given (string) (rename-able!)
         *
         * @param force
         *              The force with which the worm jumps off
         *
         * @param velocity
         *              The velocity with which the worm jumps off
         *
         * @param distance
         *              The distance the worm jumps in meters
         *
         * @param time
         *              The time the worm is in the air while jumping in seconds
         *
         * @constant density
         *              The constant density a worm has (1062 kg/m³)
         *
         * @constant min_radius
         *              The minimum radius every worm has to have in meters (0.25m)
         *
         * @constant g
         *              The Earth's standard acceleration (9.80665 m/s²)
         *
         */
                        //Declarations
						private double pos_x, pos_y, angle, radius, min_Radius, density;
                                {density = 1062;
                                 min_Radius = 0.25;}
                               
						private int Current_AP;
                       
                        private String name = "";
                       
                        private double g = 9.80665;
                       
                        private double Force;
                       
                        private double velocity;
                       
                        private double distance;
                       
                        private double Time;

                       
         /**
         *   
         * The method to set every feature of the worm to the corresponding values of his position, his radius, the direction he is looking at and his name. Here we give the worm
         * his amount of Action Points, equal to his mass.
         * 
         * @param pos_x
         * 		The x-coordinate of the worm in meters
         * 
         * @param pos_y
         * 		The y-coordinate of the worm in meters
         * 
         * @param radius
         * 		The radius of the worm in meters
         *
         * @param angle
         * 		The angle the worm is facing in radial
         * 
         * @param name
         * 		The name a worm has been given (string) (rename-able!)
         * 
         * @effect this.setPos_x(pos_x)
         * 		The worm gets placed on the X-position given to him by calling out this.setPos_x(pos_x)
         * 
         * @effect this.setPos_y(pos_y)
         * 		The worm gets placed on the Y-position given to him by calling out this.setPos_x(pos_x)
         * 
         * @effect this.setRadius(radius)
         * 		The worm gets his radius given to him by calling out this.setRadius(radius)
         * 
         * @effect this.setAngle(angle)
         * 		The worm gets his angle given to him by calling out this.setAngle(angle)
         * 
         * @effect this.setName(name)
         * 		The worm gets his name given to him by calling out this.setName(name)
         * 
         * @effect this.setCurrent_AP((int)Math.round(this.getMass()))
         * 		The worm gets his current AP given to him, equal to his mass rounded up to the next integer by calling out this.setCurrent_AP((int)Math.round(this.getMass()))
         * 
         */               
        public Worm(double pos_x, double pos_y, double radius, double angle, String name)
        {
                this.setPos_x(pos_x);
                this.setPos_y(pos_y);
                this.setRadius(radius);
                this.setAngle(angle);
                this.setName(name);
                this.setCurrent_AP((int)Math.round(this.getMass()));
                this.setHP(this.getMax_HP());
        }
        
        
		/**
         * The method to calculate the cost of action points for a possible move. The cost is equal to the solution of the following formula:
         * 		|cos(angle) + 4*sin(angle)| * nbSteps
         * The result is then rounded upwards to the next integer and given to the parameter cost.
         * 
         * @param cost
         * 		The amount of action points needed to perform this action
         * 
         * @param nbSteps
         * 		The amount of steps a worm moves
         * 
         * @return the parameter 'Cost' calculated earlier in the method
         * 
         */
        public int calculateApCostMove (int nbSteps) 
        {
                int cost = (int) Math.ceil((Math.abs(Math.cos(this.getAngle())) + 4*Math.abs(Math.sin(this.getAngle())))*nbSteps);
                return cost;
        }
       
        /**
         * The method to calculate the cost of action points for a possible turn. The cost is equal to the solution of the following formula:
         * 		|angle|*60 / (2 * PI)  (we have to take the absolute form of angle because angle could be negative)
         * The result is then rounded upwards to the next integer and given to the parameter cost.
         * 
         * @param cost
         * 		The amount of action points needed to perform this action
         * 
         * @param angle
         * 		The angle of direction a worm is facing
         * 
         * We make the split of when the angle is smaller than or equal to PI to make sure that the method also works when the angle is negative
         * 
         * @return the parameter 'Cost' calculated earlier in the method
         * 
         */
        
        public int calculateApCostTurn (double angle)
        {
        	int cost; 
        	if (Util.fuzzyLessThanOrEqualTo(Math.abs(angle), Math.PI))
        		cost = (int) Math.ceil(Math.abs(angle)*60/(2*Math.PI));
        		else
        			cost = (int) Math.ceil(Math.abs(angle)*60/(2*Math.PI));      	
                
        	return cost;
        }
       
                       
        /**
         * We follow up by declaring the methods to inspect every parameter
         * If the parameter follows every constraint on it, the result of the inspect method
         * is true, else it is false.
         */
                        //Constraint methods
        
				
				        /**
				         * The method to inspect if a movement can be made by a worm. There are 2 different cases that can occur: 
				         * 		- The amount of steps is smaller than 0, then the method move cannot be performed due to step restrictions
				         * 		- The worm has enough action points to perform this move action since the cost of movement is smaller than his current amount of action points
				         * 		  In this case we should say the method move can be performed. If this is not the case, we should say the method cannot be performed due to action point restrictions 
				         * 
				         * @param nbSteps
				         * 		The amount of steps a worm moves
				         * 
				         * @return a boolean value: if the restrictions are met, then we should return true. If not, we should return false
				         */
				        
				        public boolean inspectMovement (int nbSteps) 
				        {
				                        if (nbSteps < 0)
				                        	return false;
				                        if (calculateApCostMove(nbSteps) <= this.getCurrent_AP())
				                        	return true;
				                        else
				                        	return false;
				        }
				       
				        /**
				         * The method to inspect whether the worm can make a possible turn. We use the calculated action points cost in method calculateApCostTurn to see if the amount of current
				         * action points is larger or equal to the cost to make this turn. If this is the case, the method turn can be initiated, if not, we cannot initiate that method.
				         *
				         *
				         * @param angle
				         * 		The angle of direction a worm is facing
				         * 
				         * @return true if and only if the amount of current action points is larger than or equal to the cost of this turn
				         *
				         */
				        public boolean inspectTurn(double angle) 
				        {
				        	if (this.getCurrent_AP() >= calculateApCostTurn(angle))
				        		return true;
				        	else
				        		return false;
				        }            
                       
                        /**
                         * The method to inspect whether the radius is following all constraints
                         *
                         * @return true if and only if the radius is bigger than or equal to the minimum radius and different from the positive value of infinity
                         *
                         */
                       
                        //Inspect Radius
                        public boolean InspectRadius() 
                        {
                                if ((this.getmin_Radius() <= this.getRadius()) && (Double.POSITIVE_INFINITY != this.getRadius()))
                                        return true;
                                else return false;
                        }
                       
                        /**
                         * The method to inspect whether the name is following all constraints
                         * 
                         * @Param name
                         * 		The name a worm has been given (rename-able!)
                         *
                         * @return true if and only if the name is longer than 2 characters, starts with an uppercase character and only consists of letters, spaces or single/double quotes
                         *
                         */
                       
                        //Inspect Name
                        public boolean InspectName(String name) 
                        {
                                if (name.length() < 2)
                                        return false;
                               
                                else if (!Character.isUpperCase(name.charAt(0)))
                                        return false;
                               
                                else if (!name.matches("[a-zA-Z'\" ]*"))
                                                return false;
                               
                                else return true;
                        }
 
                       
                        /**
                         * The method to inspect whether the position of the worm is following all constraints
                         *
                         * @return true if and only if the values of pos_x and pos_y are either 0 or between 0 and positive infinity
                         *
                         */
                       
                        //Inspect Position
                        public boolean InspectPosition() 
                        {
                                if ((this.getPos_x() != Double.NEGATIVE_INFINITY) && (this.getPos_y() != Double.NEGATIVE_INFINITY) && (this.getPos_x() != Double.POSITIVE_INFINITY) && (this.getPos_y() != Double.POSITIVE_INFINITY))
                                        return true;
                                else return false;
                        }
                       
                        /**
                         * The method to inspect whether the mass of a worm is following all constraints
                         *
                         * @return true if and only if the value of mass is bigger than or equal to the density * (4/3) * PI * min_Radius³
                         *
                         */
                       
                        //Inspect Mass
                        public boolean InspectMass()
                        {
                                if (density * (4/3) * Math.PI * Math.pow(this.getmin_Radius(),3) <= this.getMass())
                                        return true;
                                else return false;
                        }
                       
                        /**
                         * The method to inspect whether the action points of a worm are following all constraints
                         *
                         * @return true if and only if the value of Current_AP is bigger than zero and smaller or equal to the maximum amount possible
                         */
                       
                        //Inspect Action Points
                        public boolean InspectActionPoints() 
                        {
                                if ((0 <= this.getCurrent_AP()) && (this.getCurrent_AP() <= this.getMax_AP()))
                                        return true;
                                else return false;
                        }
 
                       
                       
                       
        /**
         * The next thing in line are the methods for the actual gameplay, namely the method to move the worm, to turn the worm,
         * to jump with the worm and the actual physics calculations of JumpStep and JumpTime.
         * These methods will not return any values, but will change the given values into new ones.
         */
                       
                        //Methods for gameplay
                       
                        /**
                         * The method to move the worm into a certain new position if he has enough action points to use.
                         *
                         * @param nbSteps
                         *  	The amount of steps a worm can take with his current amount of action points
                         *              
                         * @throws IllegalArgumentException
                         * 		If the worm does not have enough AP left to do this step
                         *
                         * @post
                         *      If the worm has more than 0 action points, he can take a step to the direction he is facing. The cost of this step is calculated by the fact that
                         *      1 step in the X-direction costs him 1 action point and 1 step in the Y-direction costs him 4 action points.
                         *      Every time a step is taken, the parameter 'nbSteps' increases by 1 and the position gets changed and the action points get reduced.
                         *
                         */
                       
                        //Move
                        public void Move(int nbSteps)  throws IllegalArgumentException 
                        {
                                this.setPos_x(getPos_x() + (nbSteps * getRadius()) * Math.cos(this.getAngle()));
                                this.setPos_y(getPos_y() + (nbSteps * getRadius()) * Math.sin(this.getAngle()));
                                this.setCurrent_AP( this.getCurrent_AP() - calculateApCostMove(nbSteps) );
                                
                                if(inspectMovement(nbSteps) != true)                                    
                                    throw new IllegalArgumentException("You cannot do another step, you don't have enough Action Points");
                       }
                       
                        /**
                         * The method to turn the worm to a certain new direction if he has enough action points to do so.
                         *
                         * @param newangle
                         *  	The new angle a worm gets after changing it in game. This new angle is used to calculate the cost of this turn
                         *  
                         * @effect this.angle = this.getAngle() + newangle
                         * 		Sets the old angle to the new angle calculated by adding the parameter newangle to the old angle
                         * 
                         * @throws IllegalArgumentException
                         * 		If the worm does not have enough AP to do this turn
                         *
                         * @post
                         *      If the worm has enough AP to do so, he will turn and change his angle to the new angle, reducing his AP by the calculated amount in calculateApCostTurn.
                         *      To make sure that a worm also loses AP when the new angle is negative, the absolute value of this angle-difference is taken to calculate its cost.
                         *            
                         */
                       
                        //Turn
                        public void Turn(double newangle) throws IllegalArgumentException 
                        {
                                if (inspectTurn(newangle) != true )
                                        throw new IllegalArgumentException("Insufficient Action Points");
                                       
                                this.angle = this.getAngle() + newangle;
                                this.Current_AP = this.getCurrent_AP() - calculateApCostTurn(Math.abs(newangle - this.angle));        
                        }
 
                        /**
                         * The method to let the worm jump from his old location to the new location calculated in JumpStep.
                         * 
                         * @effect this.setPos_x(newPosition[0]);
                         * 		Sets the X-coordinate to the newly calculated one from JumpStep
                         * 
                         * @effect this.setPos_y(newPosition[1]);
                         * 		Sets the Y-coordinate to the newly calculated one from JumpStep
                         * 
                         * @effect this.setCurrent_AP(0)
                         * 		Sets the current amount of AP to zero after jumping
                         *
                         * @post
                         * 		If the worm has enough AP to do so and the worm is facing upwards (restrictions in JumpStep) he will jump to the newly calculated position and lose
                         * 		all his current AP.
                         */
                       
                        //Jump
                        public void Jump() 
                        {
	                            double [] newPosition = this.JumpStep(this.JumpTime());
	                            this.setPos_x(newPosition[0]);
	                            this.setPos_y(newPosition[1]);
	                            this.setCurrent_AP(0);
	                    }
       
                       
                        /**
                         * The method to return the time the worm is in the air during a certain jump.
                         * 
                         * @effect this.setForce((5 * this.getCurrent_AP()) + (this.getMass() * this.g))
                         * 		The force is set to the value equal to the solution of the formula:
                         * 			5 * this.getCurrent_AP()) + (this.getMass() * this.g
                         * 
                         * @effect this.setVelocity(this.getForce()/this.getMass()*0.5)
                         * 		The velocity is set to the value equal to the solution of the formula:
                         * 			this.getForce()/this.getMass()*0.5
                         * 
                         * @effect this.setDistance( (Math.pow(this.getVelocity(), 2) * Math.sin(2*this.getAngle()) ) / this.g)
                         * 		The distance is set to the value equal to the solution of the formula:
                         * 			(Math.pow(this.getVelocity(), 2) * Math.sin(2*this.getAngle()) ) / this.g
                         * 
                         * @effect this.setTime(this.getDistance() / (this.getVelocity() * Math.cos(this.getAngle()) ) )
                         * 		The time is set to the value equal to the solution of the formula:
                         * 			 this.getDistance() / (this.getVelocity() * Math.cos(this.getAngle()) )
                         * 
                         *
                         * @return 
                         * 		If the worm has enough AP to do this jump: the parameter 'Time' calculated that gives the duration of air-time during a jump
                         * 		If not: return zero as there will be no jump, so also no Time in the air
                         *
                         */
                       
                        //JumpTime
                        public double JumpTime()
                        {
                        	if (this.getCurrent_AP() != 0)
                        	{
                            	this.setForce((5 * this.getCurrent_AP()) + (this.getMass() * this.g));
                                this.setVelocity(this.getForce()/this.getMass()*0.5);
                                this.setDistance( (Math.pow(this.getVelocity(), 2) * Math.sin(2*this.getAngle()) ) / this.g);
                                this.setTime(this.getDistance() / (this.getVelocity() * Math.cos(this.getAngle()) ) );
                                return this.getTime();
                             }
                        
                        	else 
                        	{
                        	return 0.0;
                        	}
                        }

                       
                        /**
                         * The method to return the in flight positions for X and Y for the worm at all times
                         *
                         * @param Delta_T
                         *      The amount of time that has passed from the start of the jump till now
                         *
                         * @param Velocity_x
                         *      The amount of speed the worm has while moving in the X-direction
                         *      Equal to velocity * cos(angle)
                         *
                         * @param Velocity_y
                         *      The amount of speed the worm has while moving in the Y-direction
                         *      Equal to velocity * sin(angle)
                         *
                         * @param x
                         *      The amount of distance passed since the beginning of the jump in the X-direction
                         *      Equal to pos_x + (velocity_x * Delta_T)
                         *
                         * @param y
                         *    	The amount of distance passed since the beginning of the jump in the Y-direction
                         *      Equal to pos_y + (velocity_y * Delta_T - 0.5 * g * Delta_T²)
                         *              
                         * @param JumpStep[]
                         * 		The array of the two newly calculated coordinates x and y	
                         * 
                         * @return 
                         * 		If the angle is between zero and PI, JumpStep will return the newly calculated parameter JumpStep[]
                         * 		If not, JumpStep will return the old position of the worm, he will not move
                         *
                         */
                       
                        //JumpStep
                        public double[] JumpStep(double Delta_T)
                        {             		
                            double velocity_x = this.getVelocity() * Math.cos(this.getAngle());
                            double velocity_y = this.getVelocity() * Math.sin(this.getAngle());
                            double x = this.getPos_x() + (velocity_x * Delta_T);
                            double y = this.getPos_y() + (velocity_y * Delta_T - 0.5*this.g*Math.pow(Delta_T, 2));
                            double jumpstep[] = new double[] {x,y};
                            
                            if (Util.fuzzyLessThanOrEqualTo(0, this.getAngle())
                				&& (Util.fuzzyLessThanOrEqualTo(this.getAngle(), Math.PI)))
                            	return jumpstep;
                            
                            else return new double[] {this.getPos_x(),this.getPos_y()};
                        }
                        
                       
                       
                        /**
                         * Last, we put together all the set and get methods to retrieve the values of our parameters declared in the beginning
                         */
                       
                        //Set and Get Methods
                       
                        //Get Max_AP
                        /**
                         * This method recalls the value of the maximum amount of AP a worm can have, namely the mass a worm has rounded upwards to the next integer
                         * 
                         * @return Max_AP
                         * 		Equal to this.getMass() rounded upwards to the next integer
                         */
                        public int getMax_AP() 
                        {
                                return (int)Math.ceil(this.getMass());
                        }
                       
                        
                        //Set and Get Angle
                        /**
                         * This method recalls the value of the angle the worm is facing
                         * 
                         * @return angle
                         */
                        public double getAngle() 
                        {
                                return angle;
                        }
                        
                        /**
                         * This method sets the value for the angle a worm is facing. If the angle surpasses the value of PI, the angle gets reset by extracting PI
                         * 
                         * @param angle
                         * 		The angle of direction a worm is facing
                         * 
                         * @effect 
                         * 		Sets the value of the worms angle to a newly calculated or given angle
                         */
                        public void setAngle(double angle) 
                        {
                        	if (angle > Math.PI) 
                        		this.angle = angle - Math.PI;
                        	else
                                this.angle = angle;
                        }
                       
                       
                        //Set and Get Radius
                        /**
                         * This method recalls the value of the radius of a worm
                         * 
                         * @return radius
                         */
                        public double getRadius() 
                        {
                                return radius;
                        }
                        
                        /**
                         * This method sets the value for the radius of the worm
                         * 
                         * @param radius
                         * 		The radius a worm has been given in meters
                         * 
                         * @effect 
                         * 		Sets the value of the worms radius to a newly calculated or given radius
                         */
                        public void setRadius(double radius) 
                        {
                                this.radius = radius;
                        }
                        
                        
                        //Set and Get min_Radius
                        /**
                         * This method recalls the value of the minimal radius a worm needs to have
                         * 
                         * @return min_Radius
                         */
                        public double getmin_Radius() 
                        {
                                return min_Radius;
                        }
                        
                        /**
                         * This method sets the value for the minimal radius a worm needs to have to 0.25 meters
                         * 
                         * @param min_Radius
                         * 		The minimal radius a worm needs to have in meters (0.25 meters)
                         * 
                         * @effect 
                         * 		Sets the value of the worms minimal radius to 0.25 meters
                         */
                        public void setmin_Radius(double min_Radius) 
                        {
                                        this.min_Radius = 0.25;
                        }
                       
                        //Get Mass
                        /**
                         * This method recalls the value of the mass a worm has, equal to density*(4/3)*Math.PI*Math.pow(getRadius(), 3)
                         * 
                         * @return mass
                         * 		This mass is equal to density*(4/3)*Math.PI*Math.pow(getRadius(), 3) and therefore we have shortened the code by just putting the formula
                         */
                        public double getMass() 
                        {
                                return density*(4/3)*Math.PI*Math.pow(getRadius(), 3);
                        }
                      
                       
                        //Set and Get Pos_X
                        /**
                         * This method recalls the X-position of the worm
                         * 
                         * @return pos_x
                         */
                        public double getPos_x() 
                        {
                                return pos_x;
                        }
                        
                        /**
                         * This method sets the value for the X-position of the worm
                         * 
                         * @param pos_x
                         * 		The X-position of the worm in meters
                         * 
                         * @effect 
                         * 		Sets the value of the worms X-position to a newly calculated or given X-position
                         */
                        public void setPos_x(double pos_x) 
                        {
                                this.pos_x = pos_x;
                        }
                       
                       
                        //Set and Get Pos_Y
                        /**
                         * This method recalls the Y-position of the worm
                         * 
                         * @return pos_y
                         */
                        public double getPos_y() 
                        {
                                return pos_y;
                        }
                        
                        /**
                         * This method sets the value for the Y-position of the worm
                         * 
                         * @param pos_y
                         * 		The Y-position of the worm in meters
                         * 
                         * @effect 
                         * 		Sets the value of the worms Y-position to a newly calculated or given Y-position
                         */
                        public void setPos_y(double pos_y) 
                        {
                                this.pos_y = pos_y;
                        }
                       
                       
                        //Set and Get Current_AP
                        /**
                         * This method recalls the current amount of AP a worm has
                         * 
                         * @return current_AP
                         * 
                         */
                        public int getCurrent_AP() 
                        {
                                return Current_AP;
                        }
                        
                        /**
                         * This method sets the value for the current amount of AP a worm has
                         * 
                         * @param current_AP
                         * 		The current amount of AP a worm has
                         * 
                         * @effect 
                         * 		Sets the value of the worms current AP to the newly calculated or given current AP
                         */
                        public void setCurrent_AP(int current_AP) 
                        {
                                Current_AP = current_AP;
                        }
                       
                       
                        //Set and Get Name
                        /**
                         * This method recalls the given name of the worm
                         * 
                         * @return name
                         */
                        public String getName() 
                        {
                                return name;
                        }
                        
                        /**
                         * This method sets the name of the worm
                         * 
                         * @param name
                         * 		The name a worm is given
                         * 
                         * @throws IllegalArgumentException
                         * 		If the name of the worm does not follow his constraints of being longer than 2 characters, starting with an uppercase character and consisting
                         * 		only of letters, single or double quotes and spaces
                         * 
                         * @effect 
                         * 		Sets the value of the worms name to a newly given name if this name is valid
                         */
                        public void setName (String name) throws IllegalArgumentException 
                        {
                                        if (InspectName(name) == false)
                                                throw new IllegalArgumentException("Your name has some invalid characters included");
                                this.name = name;
                        }
                       
                       
                        //Set and Get G
                        /**
                         * This method recalls the earth acceleration which is a standard of 9.80665
                         * 
                         * @return g
                         */
                        public double getG() 
                        {
                                return g;
                        }
                        
                        /**
                         * This method sets the value for the earth acceleration
                         * 
                         * @param g
                         * 		The constant amount of earth acceleration: 9.80665
                         * 
                         * @effect 
                         * 		Sets the value of the earth acceleration to the constant one of 9.80665
                         */
                        public void setG(double g) 
                        {
                                this.g = g;
                        }
                       
                       
                        //Set and Get Force
                        /**
                         * This method recalls the force with which a worm jumps
                         * 
                         * @return force
                         */
                        public double getForce() 
                        {
                                return Force;
                        }
                        
                        /**
                         * This method sets the value for the force with which a worm jumps
                         * 
                         * @param Force
                         * 		The force with which a worm jumps
                         * 
                         * @effect 
                         * 		Sets the value of the force with which a worm jumps to the newly calculated or given force
                         */
                        public void setForce(double force) 
                        {
                                this.Force = force;
                        }
                       
                       
                        //Set and Get Velocity
                        /**
                         * This method recalls the value of the velocity with which a worm jumps
                         * 
                         * @return velocity
                         */
                        public double getVelocity() 
                        {
                                return velocity;
                        }
                        
                        /**
                         * This method sets the value for the velocity with which a worm jumps
                         * 
                         * @param velocity
                         * 		the velocity with which a worm jumps
                         * 
                         * @effect 
                         * 		Sets the value of velocity with which a worm jumps to the newly calculated or given velocity
                         */
                        public void setVelocity(double velocity) 
                        {
                                this.velocity = velocity;
                        }
                       
                       
                        //Set and Get Distance
                        /**
                         * This method recalls the value of the distance a worm jumps
                         * 
                         * @return distance
                         */
                        public double getDistance() 
                        {
                                return distance;
                        }
                        
                        /**
                         * This method sets the value for the distance a worm jumps
                         * 
                         * @param distance
                         * 		the distance a worm jumps
                         * 
                         * @effect 
                         * 		Sets the value of distance a worm jumps to the newly calculated or given distance
                         */
                        public void setDistance(double distance) 
                        {
                                this.distance = distance;
                        }
                       
                       
                        //Set and Get Time
                        /**
                         * This method recalls the value of the time a worm is in the air while jumping
                         * 
                         * @return time
                         */
                        public double getTime() 
                        {
                                return Time;
                        }
                        
                        /**
                         * This method sets the value for the time a worm is in the air while jumping
                         * 
                         * @param time
                         * 		The time a worm is in the air while jumping
                         * 
                         * @effect 
                         * 		Sets the value of the time a worm is in the air while jumping to the newly calculated or given time
                         */
                        public void setTime(double time) 
                        {
                                Time = time;
                        }
                        
                        
                        
                        
                        
/************************************************************************************************************************        
                        					// PART 2 EXTRA ADDITIONS
*************************************************************************************************************************/
                        
                        private int Current_HP;
                        
                        private String SelectedWeapon;
                        
                        public void setSelectedWeapon(String selectedweapon)
                        {
                        	this.SelectedWeapon = selectedweapon;
                        }
                        
						public String getSelectedWeapon() 
						{
							
							return SelectedWeapon;
						}

						public int getMax_HP() 
						{
                            return (int)Math.ceil(this.getMass());
						}

						public int getHP() 
						{
							return Current_HP;
						}

						public boolean isAlive() 
						{
							return (this.getHP() > 0);
						}
						
						private void setHP(int HP) 
						{
				        	if (HP < 0) this.Current_HP = 0; //OVERKILL
				        	this.Current_HP = HP;
						}
						
}
                               
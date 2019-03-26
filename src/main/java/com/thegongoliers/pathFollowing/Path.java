 package com.thegongoliers.pathFollowing;

 import com.thegongoliers.annotations.UsedInCompetition;
 import com.thegongoliers.input.odometry.DistanceSensor;
 import com.thegongoliers.output.interfaces.Drivetrain;
 import com.thegongoliers.pathFollowing.controllers.MotionController;
 import edu.wpi.first.wpilibj.command.Subsystem;
 import edu.wpi.first.wpilibj.interfaces.Gyro;

 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.Objects;

 @UsedInCompetition(team = "5112", year = "2018")
 public class Path implements Iterable<PathTaskCommand> {
     private List<PathTaskCommand> path;
     private Subsystem subsystem;
     private Drivetrain drivetrain;
     private Gyro gyro;
     private DistanceSensor encoder;
     private MotionController turnController, straightController;

     public Path(Subsystem subsystem, Drivetrain drivetrain, Gyro gyro, DistanceSensor encoder, MotionController turnController, MotionController straightController) {
         path = new LinkedList<>();
         this.subsystem = subsystem;
         this.drivetrain = drivetrain;
         this.gyro = gyro;
         this.encoder = encoder;
         this.turnController = turnController;
         this.straightController = straightController;
     }

     public void addStraightAway(double distance) {
         path.add(new PathStraightAwayCommand(subsystem, drivetrain, encoder, straightController, distance));
     }

     public void addRotation(double degrees) {
         path.add(new PathRotateCommand(subsystem, drivetrain, gyro, turnController, degrees));
     }

     public void addStraightAway(double distance, double timeout) {
         path.add(new PathStraightAwayCommand(subsystem, drivetrain, encoder, straightController, distance, timeout));
     }

     public void addRotation(double degrees, double timeout) {
         path.add(new PathRotateCommand(subsystem, drivetrain, gyro, turnController, degrees, timeout));
     }

     @Override
     public Iterator<PathTaskCommand> iterator() {
         return path.iterator();
     }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         Path that = (Path) o;
         return Objects.equals(path, that.path) &&
                 Objects.equals(drivetrain, that.drivetrain);
     }

     @Override
     public String toString() {
         return "Path{" +
                 "path=" + path +
                 '}';
     }
 }

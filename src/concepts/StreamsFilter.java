package concepts;

import model.Project;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamsFilter {

    public void assignRank() {
        List<Project> projectsList = mapProjects();
        Set<Integer> uniqueScores = new HashSet<>();
        AtomicInteger ranks = new AtomicInteger(0);

        // projectList is sorted by scores. Check In loop output
        projectsList.stream().forEach(project -> {
                    System.out.println("In loop:" + project.getScore());
                    if (!uniqueScores.contains(project.getScore())) {
                        uniqueScores.add(project.getScore());
                        project.setRank(ranks.addAndGet(1));
                    } else {
                        project.setRank(ranks.get());
                    }
                }
        );

        // Ranks will be assigned incorrectly
        projectsList.forEach(project -> {
            System.out.println("score:" + project.getScore() + "-" + "rank:" + project.getRank());
        });


        // Resetting the variables
        uniqueScores.clear();
        ranks.set(0);

        //ProjectList is sorted before applying forEach. Now In loop output will be in sequence
        projectsList.stream().sorted(Comparator.comparing(Project::getScore)).forEach(project -> {
                    System.out.println("In loop:" + project.getScore());
                    // If score is new we increment the rank. Otherwise we use the same rank
                    if (!uniqueScores.contains(project.getScore())) {
                        uniqueScores.add(project.getScore());
                        project.setRank(ranks.addAndGet(1));
                    } else {
                        project.setRank(ranks.get());
                    }
                }
        );

        projectsList.forEach(project -> {
            System.out.println("score:" + project.getScore() + "-" + "rank:" + project.getRank());
        });
    }

    private List<Project> mapProjects() {
        List<Project> projectsList = new ArrayList<>();
        //Added scored in unsorted manner
        projectsList.add(createNewProject(1));
        projectsList.add(createNewProject(1));
        projectsList.add(createNewProject(2));
        projectsList.add(createNewProject(4));
        projectsList.add(createNewProject(3));
        projectsList.add(createNewProject(3));
        projectsList.add(createNewProject(4));

        return projectsList;
    }

    private Project createNewProject(int score) {
        Project project = new Project();
        project.setScore(score);
        return project;
    }
}

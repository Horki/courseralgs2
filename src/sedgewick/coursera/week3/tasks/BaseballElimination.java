package sedgewick.coursera.week3.tasks;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseballElimination {
    private final int numberOfTeams;
    // TODO: change with BiDirectional map
    private final HashMap<String, Integer> teams;
    private final HashMap<Integer, String> teamsIdx;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] games;
    // other
    private final int numOfVertices;
    private int maxWinIdx;
    private final boolean[] isEliminated;
    private final ArrayList<Bag<String>> certificateOfElimination;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        maxWinIdx = -1;
        int maxWin = -1;
        // Parsing file START
        numberOfTeams = in.readInt();
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        teams = new HashMap<>(numberOfTeams);
        teamsIdx = new HashMap<>(numberOfTeams);
        games = new int[numberOfTeams][numberOfTeams];

        for (int teamIdx = 0; teamIdx < numberOfTeams; ++teamIdx) {
            String teamName = in.readString();
            teams.put(teamName, teamIdx);
            teamsIdx.put(teamIdx, teamName);
            wins[teamIdx] = in.readInt();
            if (wins[teamIdx] > maxWin) {
                maxWin = wins[teamIdx];
                maxWinIdx = teamIdx;
            }
            losses[teamIdx] = in.readInt();
            remaining[teamIdx] = in.readInt();
            for (int otherTeamIdx = 0; otherTeamIdx < numberOfTeams; ++otherTeamIdx) {
                games[teamIdx][otherTeamIdx] = in.readInt();
            }
        }
        // Parsing file END
        // Calculate START
        numOfVertices = (((numberOfTeams - 1) * (numberOfTeams - 2)) / 2) + numberOfTeams + 2;
        certificateOfElimination = new ArrayList<>(numberOfTeams);
        for (int j = 0; j < numberOfTeams; ++j) {
            certificateOfElimination.add(null);
        }
        isEliminated = new boolean[numberOfTeams];
        for (int teamIdx = 0; teamIdx < numberOfTeams; ++teamIdx) {
            calculate(teamIdx);
        }
        // Calculate END
    }

    private void calculate(int teamIdx) {
        if (wins[teamIdx] + remaining[teamIdx] < wins[maxWinIdx]) {
            isEliminated[teamIdx] = true;
            Bag<String> bag = new Bag<>();
            bag.add(teamsIdx.get(maxWinIdx));
            certificateOfElimination.set(teamIdx, bag);
            return;
        }

        FlowNetwork flowNetwork = createFlowNetwork(teamIdx);
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, numOfVertices - 2, numOfVertices - 1);
        for (int k = 0; k < numberOfTeams; ++k) {
            if (!fordFulkerson.inCut(k)) {
                continue;
            }
            isEliminated[teamIdx] = true;
            String team = teamsIdx.get(k);
            if (certificateOfElimination.get(teamIdx) == null) {
                Bag<String> bag = new Bag<>();
                bag.add(team);
                certificateOfElimination.set(teamIdx, bag);
            } else {
                certificateOfElimination.get(teamIdx).add(team);
            }
        }
    }

    private FlowNetwork createFlowNetwork(int x) {
        FlowNetwork flowNetwork = new FlowNetwork(numOfVertices);
        // Source
        int s = numOfVertices - 2;
        // Destination
        int t = numOfVertices - 1;
        // add edges
        for (int i = 0; i < numberOfTeams; i++) {
            if (i == x) {
                continue;
            }
            double capacity = wins[x] + remaining[x] - wins[i];
            flowNetwork.addEdge(new FlowEdge(i, t, capacity));
        }

        int gameIdx = numberOfTeams;
        for (int i = 0; i < numberOfTeams - 1; i++) {
            if (i == x) {
                continue;
            }
            for (int j = i + 1; j < numberOfTeams; j++) {
                if (j == x) {
                    continue;
                }
                flowNetwork.addEdge(new FlowEdge(gameIdx, i, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(gameIdx, j, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(s, gameIdx, games[i][j]));
                ++gameIdx;
            }
        }
        return flowNetwork;
    }


    // number of teams
    public int numberOfTeams() {
        return numberOfTeams;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        return wins[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return losses[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return remaining[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return games[teams.get(team1)][teams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return isEliminated[teams.get(team)];
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return certificateOfElimination.get(teams.get(team));
    }

    // teams4.txt
    // teams5.txt
    // teams12.txt
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        StdOut.println("Number of teams: " + division.numberOfTeams());
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + ", ");
                }
                StdOut.println("}");
                StdOut.println(String.format("[%s] Wins = %d, Losses = %d, Remaining = %d",
                        team, division.wins(team), division.losses(team), division.remaining(team)));
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(division.against(team, t) + ", ");
                }
                StdOut.println();

            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}

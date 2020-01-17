package com.natasha;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Labyrinth {

    public static int row, column;
    public static boolean North, West, South = true, East;
    public static boolean fromNorth = true, fromEast, fromSouth, fromWest;


    public static void turnToLeft() {
        if (North) {
            West = true;
            North = false;
        } else if (West) {
            South = true;
            West = false;
        } else if (South) {
            East = true;
            South = false;
        } else if (East)  {
            North = true;
            East = false;
        }
    }

    public static void turnToRight() {
        if (North) {
            East = true;
            North = false;
        } else if (West) {
            North = true;
            West = false;
        } else if (South) {
            West = true;
            South = false;
        }  else if (East) {
            South = true;
            East = false;
        }
    }

    public static void turnToBack()  {
        if (North) {
            South = true;
            North = false;
        } else if (West) {
            East = true;
            West = false;
        } else if (South) {
            North = true;
            South = false;
        } else if (East) {
            West = true;
            East = false;
        }
    }
    public static void setFalse()
    {
        fromEast = false;
        fromNorth = false;
        fromSouth = false;
        fromWest = false;
    }

    public static Room move(Room room) {
        if (North) {
            room.North = true;
            row--;
            setFalse();
            fromSouth = true;
        } else if (South) {
            room.South = true;
            row++;
            setFalse();
            fromNorth = true;
        } else if (West) {
            room.West = true;
            column--;
            setFalse();
            fromEast = true;
        } else if (East) {
            room.East = true;
            column++;
            setFalse();
            fromWest = true;
        }
        return room;
    }

    public static Room changePole(Room room) {
        room.North = room.North || fromNorth || North;
        room.South = room.South || fromSouth || South;
        room.West = room.West || fromWest || West;
        room.East = room.East || fromEast || East;
        return room;
    }

    public static void reset() {
        North = false; South = true; West = false; East = false;
        fromNorth = true; fromSouth = false; fromWest = false; fromEast = false;
    }

    public static String describeRoom(Room[][] room) {
        String text = "";
        for(int i = 0; i < room.length; i++)  {
            for(int j = 0; j < room[i].length; j++) {
                if (room[i][j].North && !room[i][j].South && !room[i][j].West && !room[i][j].East) text += ("1");
                    else if (!room[i][j].North && room[i][j].South && !room[i][j].West && !room[i][j].East) text += ("2");
                    else if (room[i][j].North && room[i][j].South && !room[i][j].West && !room[i][j].East) text += ("3");
                    else if (!room[i][j].North && !room[i][j].South && room[i][j].West && !room[i][j].East) text += ("4");
                    else if (room[i][j].North && !room[i][j].South && room[i][j].West && !room[i][j].East) text += ("5");
                    else if (!room[i][j].North && room[i][j].South && room[i][j].West && !room[i][j].East) text += ("6");
                    else if (room[i][j].North && room[i][j].South && room[i][j].West && !room[i][j].East) text += ("7");
                    else if (!room[i][j].North && !room[i][j].South && !room[i][j].West && room[i][j].East) text += ("8");
                    else if (room[i][j].North && !room[i][j].South && !room[i][j].West && room[i][j].East) text += ("9");
                    else if (!room[i][j].North && room[i][j].South && !room[i][j].West && room[i][j].East) text += ("a");
                    else if (room[i][j].North && room[i][j].South && !room[i][j].West && room[i][j].East) text += ("b");
                    else if (!room[i][j].North && !room[i][j].South && room[i][j].West && room[i][j].East) text += ("c");
                    else if (room[i][j].North && !room[i][j].South && room[i][j].West && room[i][j].East) text += ("d");
                    else if (!room[i][j].North && room[i][j].South && room[i][j].West && room[i][j].East) text += ("e");
                    else if (room[i][j].North && room[i][j].South && room[i][j].West && room[i][j].East) text += ("f");
            }
            text += ("\n");
        }
        text = text.replaceAll("\n\n", "");
        text += "\n";
        return text;
    }

    public static Room turn(Room room) {
        room.North = room.North || fromNorth;
        room.South = room.South || fromSouth;
        room.West = room.West || fromWest;
        room.East = room.East || fromEast;
        return room;
    }


    public static String readFile(List<String> data) {
        int countLabyrinth = Integer.parseInt(data.get(0));
        String listDescribe = "";
        for (int i = 1; i<= countLabyrinth; i++){
            reset();
            listDescribe += "Case #" + i + ":\n";
            String[] line = data.get(i).split(" ");
            int countOfW;
            long countOfW0 = line[0].chars().filter(c -> c == 'W').count();
            long countOfW1 = line[1].chars().filter(c -> c == 'W').count();
            countOfW = (countOfW0 >= countOfW1) ? (int) countOfW0 : (int) countOfW1;

            Room[][] room = new Room[countOfW + 1][countOfW * 2 + 1];
            for(int r1=0;r1<room.length;r1++ ) {
                for(int r2=0;r2<room[r1].length;r2++){
                    room[r1][r2] = new Room();
                }
            }
            row = 0;
            column = countOfW;
            for (String subline : line) {
                for (int k = 1; k < subline.length() - 1; k++) {
                    if (subline.substring(k, k + 1).equals("W")) {
                        room[row][column] = turn(room[row][column]);
                        room[row][column] = move(room[row][column]);
                    } else if (subline.substring(k, k + 1).equals("L")) {
                        room[row][column] = turn(room[row][column]);
                        turnToLeft();
                    } else if (subline.substring(k, k + 1).equals("R")) {
                        if (subline.substring(k + 1, k + 2).equals("R")) {
                            turnToBack();
                            k++;
                        } else {
                            room[row][column] = turn(room[row][column]);
                            turnToRight();
                        }
                    }
                }
                room[row][column] = changePole(room[row][column]);
                turnToBack();
            }
            listDescribe += describeRoom(room);
        }
        return listDescribe.replaceAll("\n\n", "");
    }

    public static List<String> readFileAsString(String fileName) throws Exception {
        return Files.readAllLines(Paths.get(fileName));
    }

    public static void WriteInFile(String string, String fileName){
        try(FileWriter writer = new FileWriter(fileName, false))  {
            writer.write(string);
            writer.flush();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static void main(String[] args) {
        try {
            List<String> dataSmall = readFileAsString("small-test.in.txt");
            String resultSmall = readFile(dataSmall);
            WriteInFile(resultSmall, "small-test.out.txt");

            List<String> dataLarge = readFileAsString("large-test.in.txt");
            String resultLarge = readFile(dataLarge);
            WriteInFile(resultLarge, "large-test.out.txt");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

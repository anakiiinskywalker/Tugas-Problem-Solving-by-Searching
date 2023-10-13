/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

/**
 *
 * @author anakinskywalker
 *
 */

import java.util.*;

public class PerjalananWisata {

    static class Graph {
        private Map<String, Map<String, Integer>> graph = new HashMap<>();

        public void tambahRute(String tempatAsal, String tempatTujuan, int jarak) {
            graph.putIfAbsent(tempatAsal, new HashMap<>());
            graph.get(tempatAsal).put(tempatTujuan, jarak);
        }

        public Map<String, Map<String, Integer>> getGraph() {
            return graph;
        }
    }

    public static List<String> cariRuteTerpendek(Map<String, Map<String, Integer>> graph, String tempatAsal, String tempatTujuan) {
        if (!graph.containsKey(tempatAsal) || !graph.containsKey(tempatTujuan)) {
            return null; // Tidak ada rute jika tempat asal atau tujuan tidak ditemukan
        }

        Map<String, Integer> jarakMin = new HashMap<>();
        Map<String, String> tempatSebelumnya = new HashMap<>();
        Set<String> belumDikunjungi = new HashSet<>();

        for (String tempat : graph.keySet()) {
            jarakMin.put(tempat, Integer.MAX_VALUE);
            tempatSebelumnya.put(tempat, null);
            belumDikunjungi.add(tempat);
        }

        jarakMin.put(tempatAsal, 0);

        while (!belumDikunjungi.isEmpty()) {
            String tempatSekarang = null;
            for (String tempat : belumDikunjungi) {
                if (tempatSekarang == null || jarakMin.get(tempat) < jarakMin.get(tempatSekarang)) {
                    tempatSekarang = tempat;
                }
            }

            if (tempatSekarang.equals(tempatTujuan)) {
                List<String> ruteTerpendek = new ArrayList<>();
                while (tempatSekarang != null) {
                    ruteTerpendek.add(tempatSekarang);
                    tempatSekarang = tempatSebelumnya.get(tempatSekarang);
                }
                Collections.reverse(ruteTerpendek);
                return ruteTerpendek;
            }

            belumDikunjungi.remove(tempatSekarang);

            for (String tetangga : graph.get(tempatSekarang).keySet()) {
                int jarakBaru = jarakMin.get(tempatSekarang) + graph.get(tempatSekarang).get(tetangga);
                if (jarakBaru < jarakMin.get(tetangga)) {
                    jarakMin.put(tetangga, jarakBaru);
                    tempatSebelumnya.put(tetangga, tempatSekarang);
                }
            }
        }

        return null; // Tidak ada rute yang ditemukan
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.tambahRute("Pegunungan", "Danau", 20);
        graph.tambahRute("Pegunungan", "Hutan", 15);
        graph.tambahRute("Danau", "Hutan", 10);
        graph.tambahRute("Hutan", "Pantai", 18);
        graph.tambahRute("Pantai", "Kota", 8);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tempat Asal: ");
        String tempatAsal = scanner.nextLine();
        System.out.print("Tempat Tujuan: ");
        String tempatTujuan = scanner.nextLine();

        List<String> ruteTerpendek = cariRuteTerpendek(graph.getGraph(), tempatAsal, tempatTujuan);

        if (ruteTerpendek != null) {
            System.out.println("Rute terpendek dari " + tempatAsal + " ke " + tempatTujuan + " adalah: " + ruteTerpendek);
        } else {
            System.out.println("Tidak ada rute yang tersedia dari " + tempatAsal + " ke " + tempatTujuan + ".");
        }
    }
}

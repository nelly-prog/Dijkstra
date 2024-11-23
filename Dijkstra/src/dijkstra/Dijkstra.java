package dijkstra;

import java.util.*;

public class Dijkstra {

    private final Map<String, List<Arista>> listaAdyacente = new HashMap<>();

    public void agregarArista(String origen, String destino, int peso) {
        listaAdyacente.putIfAbsent(origen, new ArrayList<>());
        listaAdyacente.putIfAbsent(destino, new ArrayList<>());
        listaAdyacente.get(origen).add(new Arista(destino, peso));
        listaAdyacente.get(destino).add(new Arista(origen, peso)); 
    }

    public Map<String, Integer> dijkstra(String inicio) {
        Map<String, Integer> distancias = new HashMap<>();
        PriorityQueue<Arista> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(arista -> arista.peso));
        Set<String> visitados = new HashSet<>();

        for (String ciudad : listaAdyacente.keySet()) {
            distancias.put(ciudad, Integer.MAX_VALUE);
        }
        distancias.put(inicio, 0);
        colaPrioridad.add(new Arista(inicio, 0));

        while (!colaPrioridad.isEmpty()) {
            Arista aristaActual = colaPrioridad.poll();
            String ciudadActual = aristaActual.destino;

            if (visitados.contains(ciudadActual)) {
                continue;
            }
            visitados.add(ciudadActual);

            for (Arista vecino : listaAdyacente.get(ciudadActual)) {
                if (!visitados.contains(vecino.destino)) {
                    int nuevaDistancia = distancias.get(ciudadActual) + vecino.peso;
                    if (nuevaDistancia < distancias.get(vecino.destino)) {
                        distancias.put(vecino.destino, nuevaDistancia);
                        colaPrioridad.add(new Arista(vecino.destino, nuevaDistancia));
                    }
                }
            }
        }
        return distancias;
    }

    public List<String> obtenerRuta(String inicio, String destino) {
        Map<String, String> anterior = new HashMap<>();
        dijkstraConRuta(inicio, anterior);

        List<String> ruta = new LinkedList<>();
        for (String en = destino; en != null; en = anterior.get(en)) {
            ruta.add(en);
        }
        Collections.reverse(ruta);
        return ruta.size() > 1 ? ruta : Collections.emptyList(); // Devuelve vacío si no hay ruta
    }

    private void dijkstraConRuta(String inicio, Map<String, String> anterior) {
        Map<String, Integer> distancias = new HashMap<>();
        PriorityQueue<Arista> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(arista -> arista.peso));
        Set<String> visitados = new HashSet<>();

        for (String ciudad : listaAdyacente.keySet()) {
            distancias.put(ciudad, Integer.MAX_VALUE);
        }
        distancias.put(inicio, 0);
        colaPrioridad.add(new Arista(inicio, 0));

        while (!colaPrioridad.isEmpty()) {
            Arista aristaActual = colaPrioridad.poll();
            String ciudadActual = aristaActual.destino;

            if (visitados.contains(ciudadActual)) {
                continue;
            }
            visitados.add(ciudadActual);

            for (Arista vecino : listaAdyacente.get(ciudadActual)) {
                if (!visitados.contains(vecino.destino)) {
                    int nuevaDistancia = distancias.get(ciudadActual) + vecino.peso;
                    if (nuevaDistancia < distancias.get(vecino.destino)) {
                        distancias.put(vecino.destino, nuevaDistancia);
                        anterior.put(vecino.destino, ciudadActual);
                        colaPrioridad.add(new Arista(vecino.destino, nuevaDistancia));
                    }
                }
            }
        }
    }

    private static class Arista {
        String destino;
        int peso;

        Arista(String destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    public static void main(String[] args) {
        Dijkstra grafo = new Dijkstra();
        
        grafo.agregarArista("A", "B", 4);
        grafo.agregarArista("A", "C", 1);
        grafo.agregarArista("B", "C", 2);
        grafo.agregarArista("B", "D", 5);
        grafo.agregarArista("C", "D", 8);
        grafo.agregarArista("C", "E", 10);
        grafo.agregarArista("D", "E", 2);
        grafo.agregarArista("D", "A", 7);
}
}

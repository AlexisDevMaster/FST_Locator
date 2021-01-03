package com.theluckydev.fst.graph;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.theluckydev.fst.SalleBatimentDatabase.Database.AppDatabase;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphEdge;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphNode;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Room;
import com.theluckydev.fst.objet.Objet;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;

import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GraphController {

    private Graph<Vertex, DefaultWeightedEdge> g;
    private HashMap<Integer, Vertex> idVertexHashap;
    private Context context;

    public GraphController(Context context) {
        idVertexHashap = new HashMap<>();
        g = new WeightedPseudograph<>(DefaultWeightedEdge.class);
        this.context = context;
    }

    public Graph<Vertex, DefaultWeightedEdge> getG() {
        return g;
    }

    public void iniByJSONParser() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("D:\\Documents\\Java_Project\\Graph\\res\\graph.json"));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray graph = (JSONArray) jsonObject.get("graph");
            JSONObject graphContent = (JSONObject) Objects.requireNonNull(graph).get(0);
            JSONArray vertex = (JSONArray) graphContent.get("vertex");

            for (Object subject : Objects.requireNonNull(vertex)) {
                JSONObject objJSON = (JSONObject) subject;
                Vertex v = new Vertex(Integer.parseInt(Objects.requireNonNull(objJSON.get("id")).toString()),
                        Objects.requireNonNull(objJSON.get("name")).toString(),
                        Double.parseDouble(Objects.requireNonNull(objJSON.get("latitude")).toString()),
                        Double.parseDouble(Objects.requireNonNull(objJSON.get("longitude")).toString()));
                g.addVertex(v);
                idVertexHashap.put(Integer.parseInt(Objects.requireNonNull(objJSON.get("id")).toString()), v);
            }

            JSONArray edge = (JSONArray) graphContent.get("edge");

            for (Object subject : Objects.requireNonNull(edge)) {
                JSONObject objJSON = (JSONObject) subject;
                g.addEdge(idVertexHashap.get(Integer.parseInt(Objects.requireNonNull(objJSON.get("vertex1")).toString())),
                        idVertexHashap.get(Integer.parseInt(Objects.requireNonNull(objJSON.get("vertex2")).toString())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initByBDD(AppDatabase mDb) {
        List<GraphNode> nodeList = mDb.graphNodeDAO().getAll();
        for (GraphNode graphNode : Objects.requireNonNull(nodeList)) {
            Vertex v = new Vertex(graphNode.getIdNode(),
                    graphNode.getName(),
                    Double.parseDouble(graphNode.getLatitude()),
                    Double.parseDouble(graphNode.getLongitude()));
            g.addVertex(v);
            idVertexHashap.put(graphNode.getIdNode(), v);
        }

        List<GraphEdge> edgeList = mDb.graphEdgeDAO().getAll();

        for (GraphEdge graphEdge : edgeList) {
            DefaultWeightedEdge edge = g.addEdge(idVertexHashap.get(graphEdge.getIdNode1()),
                    idVertexHashap.get(graphEdge.getIdNode2()));
            g.setEdgeWeight(edge, 1);
        }

        DOTExporter<Vertex, DefaultWeightedEdge> exporter =
                new DOTExporter<>(v -> ""+v.getId());
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.getName()));
            return map;
        });
        Writer writer = new StringWriter();
        exporter.exportGraph(g, writer);
        System.out.println(writer.toString());
    }

    public List<Vertex> shortestWay(int start, int destination) {
        BellmanFordShortestPath<Vertex, DefaultWeightedEdge> bellmanFordAlgo = new BellmanFordShortestPath<>(g);
        Log.i("ITINERAIRE", "id vertex 1 : " + start);
        Log.i("ITINERAIRE", "id vertex 2 : " + destination);

        GraphPath<Vertex, DefaultWeightedEdge> iPaths = bellmanFordAlgo.findPathBetween(g, idVertexHashap.get(start), idVertexHashap.get(destination));
        for(Vertex v : iPaths.getVertexList()){
            System.out.println(v.getName() + "\n");
        }
        return iPaths.getVertexList();
    }
}

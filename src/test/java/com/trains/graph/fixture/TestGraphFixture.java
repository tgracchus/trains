package com.trains.graph.fixture;

import com.trains.graph.Edge;
import com.trains.graph.Edges;
import com.trains.graph.Graph;
import com.trains.graph.Path;
import com.trains.graph.Vertex;
import com.trains.graph.traverse.Traverse;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGraphFixture {


    private final Graph testGraph;
    private final Traverse traverse;

    public TestGraphFixture(Graph testGraph, Traverse traverse) {
        this.testGraph = testGraph;
        this.traverse = traverse;

    }

    public Graph Graph() {
        return testGraph;
    }

    public void checkValidTraverse(List<Vertex> traversal, int expectedLength, int expectedtedWeight) {
        Optional<Path> traverseOpt = this.traverse.traverse(traversal, testGraph);
        assertTrue(traverseOpt.isPresent());
        Path path = traverseOpt.get();
        assertEquals(expectedLength, path.longitud());
        for (int i = 0; i < traversal.size(); i++) {
            Vertex traversalVertex = traversal.get(i);
            assertEquals(path.get(i), traversalVertex);
            assertEquals(expectedtedWeight, path.weight());
        }
    }

    public void checkInvalidTraverse(List<Vertex> traversal) {
        Optional<Path> traverseOpt = this.traverse.traverse(traversal, testGraph);
        assertFalse(traverseOpt.isPresent());
    }

    public void checkValidEdges(Vertex a, List<EdgeCheck> checks) {
        Edges edges = testGraph.edges(a);
        assertEquals(checks.size(), edges.size());
        int i = 0;
        for (EdgeCheck check : checks) {
            Optional<Edge> edgeOpt = edges.traverseTo(check.expectedTargetVertex);
            assertTrue(edgeOpt.isPresent());
            Edge edge = edgeOpt.get();
            assertEquals(check.expectedWeight, edge.getWeight());
        }


    }


    public static class EdgeCheck {
        private final Vertex expectedTargetVertex;
        private final int expectedWeight;

        private EdgeCheck(Vertex expectedTargetVertex, int expectedWeight) {
            this.expectedTargetVertex = expectedTargetVertex;
            this.expectedWeight = expectedWeight;
        }

        public static EdgeCheck newEdgeCheck(Vertex expectedTargetVertex, int expectedWeight) {
            return new EdgeCheck(expectedTargetVertex, expectedWeight);
        }
    }
}
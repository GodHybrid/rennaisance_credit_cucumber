package godhybrid.credits.Glue.plugin;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import cucumber.api.event.TestSourceRead;
import gherkin.*;
import gherkin.ast.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;

final class CucumberSourceUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(godhybrid.credits.Glue.plugin.CucumberSourceUtils.class);
    private final Map<String, TestSourceRead> pathToReadEventMap = new HashMap();
    private final Map<String, GherkinDocument> pathToAstMap = new HashMap();
    private final Map<String, Map<Integer, AstNode>> pathToNodeMap = new HashMap();

    CucumberSourceUtils() {
    }

    public void addTestSourceReadEvent(String path, TestSourceRead event) {
        this.pathToReadEventMap.put(path, event);
    }

    public Feature getFeature(String path) {
        if (!this.pathToAstMap.containsKey(path)) {
            this.parseGherkinSource(path);
        }

        return this.pathToAstMap.containsKey(path) ? ((GherkinDocument)this.pathToAstMap.get(path)).getFeature() : null;
    }

    private void parseGherkinSource(String path) {
        if (this.pathToReadEventMap.containsKey(path)) {
            Parser<GherkinDocument> parser = new Parser(new AstBuilder());
            TokenMatcher matcher = new TokenMatcher();

            try {
                GherkinDocument gherkinDocument = (GherkinDocument)parser.parse(((TestSourceRead)this.pathToReadEventMap.get(path)).source, matcher);
                this.pathToAstMap.put(path, gherkinDocument);
                Map<Integer, AstNode> nodeMap = new HashMap();
                godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode currentParent = new godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode(gherkinDocument.getFeature(), (godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode)null);
                Iterator var7 = gherkinDocument.getFeature().getChildren().iterator();

                while(var7.hasNext()) {
                    ScenarioDefinition child = (ScenarioDefinition)var7.next();
                    this.processScenarioDefinition(nodeMap, child, currentParent);
                }

                this.pathToNodeMap.put(path, nodeMap);
            } catch (ParserException var9) {
                LOGGER.trace(var9.getMessage(), var9);
            }

        }
    }

    private void processScenarioDefinition(Map<Integer, AstNode> nodeMap, ScenarioDefinition child, godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode currentParent) {
        godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode childNode = new godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode(child, currentParent);
        nodeMap.put(child.getLocation().getLine(), childNode);
        child.getSteps().forEach((step) -> {
            godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode var10000 = (godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode)nodeMap.put(step.getLocation().getLine(), new godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode(step, childNode));
        });
        if (child instanceof ScenarioOutline) {
            this.processScenarioOutlineExamples(nodeMap, (ScenarioOutline)child, childNode);
        }

    }

    private void processScenarioOutlineExamples(Map<Integer, AstNode> nodeMap, ScenarioOutline scenarioOutline, godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode childNode) {
        scenarioOutline.getExamples().forEach((examples) -> {
            godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode examplesNode = new godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode(examples, childNode);
            TableRow headerRow = examples.getTableHeader();
            godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode headerNode = new godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode(headerRow, examplesNode);
            nodeMap.put(headerRow.getLocation().getLine(), headerNode);
            IntStream.range(0, examples.getTableBody().size()).forEach((i) -> {
                TableRow examplesRow = (TableRow)examples.getTableBody().get(i);
                Node rowNode = new godhybrid.credits.Glue.plugin.CucumberSourceUtils.ExamplesRowWrapperNode(examplesRow, i);
                godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode expandedScenarioNode = new godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode(rowNode, examplesNode);
                nodeMap.put(examplesRow.getLocation().getLine(), expandedScenarioNode);
            });
        });
    }

    private godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode getAstNode(String path, int line) {
        if (!this.pathToNodeMap.containsKey(path)) {
            this.parseGherkinSource(path);
        }

        return this.pathToNodeMap.containsKey(path) ? (godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode)((Map)this.pathToNodeMap.get(path)).get(line) : null;
    }

    public ScenarioDefinition getScenarioDefinition(String path, int line) {
        return this.getScenarioDefinition(this.getAstNode(path, line));
    }

    private ScenarioDefinition getScenarioDefinition(godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode astNode) {
        return astNode.getNode() instanceof ScenarioDefinition ? (ScenarioDefinition)astNode.getNode() : (ScenarioDefinition)astNode.getParent().getParent().getNode();
    }

    public String getKeywordFromSource(String uri, int stepLine) {
        Feature feature = this.getFeature(uri);
        if (feature != null) {
            TestSourceRead event = this.getTestSourceReadEvent(uri);
            String trimmedSourceLine = event.source.split("\n")[stepLine - 1].trim();
            GherkinDialect dialect = (new GherkinDialectProvider(feature.getLanguage())).getDefaultDialect();
            Iterator var7 = dialect.getStepKeywords().iterator();

            while(var7.hasNext()) {
                String keyword = (String)var7.next();
                if (trimmedSourceLine.startsWith(keyword)) {
                    return keyword;
                }
            }
        }

        return "";
    }

    private TestSourceRead getTestSourceReadEvent(String uri) {
        return this.pathToReadEventMap.containsKey(uri) ? (TestSourceRead)this.pathToReadEventMap.get(uri) : null;
    }

    private static class AstNode {
        private final Node node;
        private final godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode parent;

        AstNode(Node node, godhybrid.credits.Glue.plugin.CucumberSourceUtils.AstNode parent) {
            this.node = node;
            this.parent = parent;
        }

        public Node getNode() {
            return this.node;
        }

        public CucumberSourceUtils.AstNode getParent() {
            return this.parent;
        }
    }

    private static class ExamplesRowWrapperNode extends Node {
        private final int bodyRowIndex;

        ExamplesRowWrapperNode(Node examplesRow, int bodyRowIndex) {
            super(examplesRow.getLocation());
            this.bodyRowIndex = bodyRowIndex;
        }

        public int getBodyRowIndex() {
            return this.bodyRowIndex;
        }
    }
}


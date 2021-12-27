package bg.unisofia.bsmarkov.ont;

import static bg.unisofia.bsmarkov.ont.CriminalStory.NS;
import static org.apache.jena.ontology.OntModelSpec.OWL_DL_MEM_RULE_INF;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

public class CriminalInference {

    /**
     * This method makes additional classes and deductions and returns the new ontology.
     */
    public static OntModel makeDeductions(OntModel inputOnt) throws IOException {
        List<Rule> rules = Utils.readRules();
        // Create reasoner based on our custom rules
        Reasoner reasoner = new GenericRuleReasoner(rules);

        InfModel inf2 = ModelFactory.createInfModel(reasoner, inputOnt);

        OntModel outOntology = ModelFactory.createOntologyModel(OWL_DL_MEM_RULE_INF, inf2);

        // Create class that identifies people which can share sensitive info
        outOntology.createIntersectionClass(NS + "LeakInfoSourcePeople",
                outOntology.createList(
                        outOntology.createHasValueRestriction(null,
                                outOntology.getOntProperty(NS + "worksFor"),
                                outOntology.getIndividual(NS + "roboSoapStrategyProject")),
                        outOntology.createMinCardinalityRestriction(null,
                                outOntology.getOntProperty(NS + "canShareSensitiveInfoIndirectTo"), 1)));

        // Identify people who published the sensitive info
        outOntology.createHasValueRestriction(NS + "LeakInfoDestinationPeople",
                outOntology.getOntProperty(NS + "worksFor"),
                outOntology.getIndividual(NS + "domesticSoapSoftwareCompany"));


        return outOntology;
    }


}

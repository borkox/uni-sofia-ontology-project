package bg.unisofia.bsmarkov.ont;

import static org.apache.jena.ontology.OntModelSpec.OWL_DL_MEM_RULE_INF;

import java.io.IOException;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.SymmetricProperty;
import org.apache.jena.rdf.model.ModelFactory;

public class CriminalStory {

    public static final String SOURCE = "urn:soap/crime/ontology";
    public static final String NS = SOURCE + "#";

    public static OntModel describeCriminalStory() throws IOException {
        OntModel inf = ModelFactory.createOntologyModel(OWL_DL_MEM_RULE_INF);
        // Prefix is giving us short name to reference
        inf.setNsPrefix("sc", NS);

        OntClass personClass = inf.createClass(NS + "Person");
        OntClass manClass = inf.createClass(NS + "Man");
        OntClass womanClass = inf.createComplementClass(NS + "Woman", manClass);
        personClass.addSubClass(manClass);
        personClass.addSubClass(womanClass);
        OntClass companyClass = inf.createClass(NS + "Company");
        OntClass projectClass = inf.createClass(NS + "Project");
        OntClass eventClass = inf.createClass(NS + "Event");
        OntClass witnessedEventClass = inf.createClass(NS + "WitnessedEvent");
        eventClass.addSubClass(witnessedEventClass);
        OntClass conversation = inf.createClass(NS + "Conversation");
        witnessedEventClass.addSubClass(conversation);
        OntClass onlineConversation = inf.createClass(NS + "OnlineConversation");
        conversation.addSubClass(onlineConversation);

        ObjectProperty worksFor = inf.createObjectProperty(NS + "worksFor");
        worksFor.setDomain(personClass);
        worksFor.setRange(companyClass);
        worksFor.addRange(projectClass);

        ObjectProperty hasConversationParticipant = inf.createObjectProperty(NS + "hasConversationParticipant");
        hasConversationParticipant.setDomain(conversation);
        hasConversationParticipant.setRange(personClass);

        ObjectProperty isPartOfConversation = inf.createObjectProperty(NS + "isPartOfConversation");
        isPartOfConversation.setDomain(personClass);
        isPartOfConversation.setRange(conversation);
        hasConversationParticipant.addInverseOf(isPartOfConversation);

        ObjectProperty canSeeMonitor = inf.createObjectProperty(NS + "canSeeMonitor");
        canSeeMonitor.setRange(personClass);
        canSeeMonitor.setDomain(personClass);

        SymmetricProperty isGoodFriendToProperty = inf.createSymmetricProperty(NS + "isGoodFriendTo");
        isGoodFriendToProperty.setDomain(personClass);
        isGoodFriendToProperty.setRange(personClass);

        ObjectProperty canShareSensitiveInfoTo = inf
                .createObjectProperty(NS + "canShareSensitiveInfoTo");
        canShareSensitiveInfoTo.setDomain(personClass);
        canShareSensitiveInfoTo.setRange(personClass);

        ObjectProperty canShareSensitiveInfoIndirectTo = inf
                .createObjectProperty(NS + "canShareSensitiveInfoIndirectTo");
        canShareSensitiveInfoTo.setDomain(personClass);
        canShareSensitiveInfoTo.setRange(personClass);

        // hasMotive is functional property
        ObjectProperty loveButNotLovedProperty = inf.createObjectProperty(NS + "loveButNotLoved", true);
        loveButNotLovedProperty.setDomain(personClass);
        loveButNotLovedProperty.setRange(personClass);

        Individual john = manClass.createIndividual(NS + "john");
        Individual sara = womanClass.createIndividual(NS + "sara");
        Individual peter = manClass.createIndividual(NS + "peter");
        Individual riki = manClass.createIndividual(NS + "riki");
        Individual laura = womanClass.createIndividual(NS + "laura");
        Individual sam = manClass.createIndividual(NS + "sam");
        Individual sophie = womanClass.createIndividual(NS + "sophie");

        Individual soapAcademyInt = companyClass.createIndividual(NS + "soapAcademyIntCompany");
        Individual domesticSoapSoft = companyClass.createIndividual(NS + "domesticSoapSoftwareCompany");
        Individual sensitiveInfoProject = projectClass.createIndividual(NS + "roboSoapStrategyProject");
        Individual eveningConversation = conversation.createIndividual(NS + "eveningConversation");
        Individual kitchenConversation = conversation.createIndividual(NS + "kitchenConversation");
        Individual peterAndJohnChat = onlineConversation.createIndividual(NS + "peterAndJohnChat");

        john.addProperty(worksFor, soapAcademyInt);
        sara.addProperty(worksFor, soapAcademyInt);
        peter.addProperty(worksFor, soapAcademyInt);
        riki.addProperty(worksFor, soapAcademyInt);
        laura.addProperty(worksFor, soapAcademyInt);
        sam.addProperty(worksFor, domesticSoapSoft);
        sophie.addProperty(worksFor, domesticSoapSoft);
        john.addProperty(worksFor, sensitiveInfoProject);
        sara.addProperty(worksFor, sensitiveInfoProject);
        peter.addProperty(worksFor, sensitiveInfoProject);
        riki.addProperty(canSeeMonitor, peter);
        laura.addProperty(loveButNotLovedProperty, john);
        peter.addProperty(loveButNotLovedProperty, laura);
        laura.addProperty(isGoodFriendToProperty, sam);
        laura.addProperty(isGoodFriendToProperty, riki);
        eveningConversation.addProperty(hasConversationParticipant, laura);
        eveningConversation.addProperty(hasConversationParticipant, sam);
        kitchenConversation.addProperty(hasConversationParticipant, laura);
        kitchenConversation.addProperty(hasConversationParticipant, riki);
        peterAndJohnChat.addProperty(hasConversationParticipant, peter);
        peterAndJohnChat.addProperty(hasConversationParticipant, john);

        return inf;
    }

}

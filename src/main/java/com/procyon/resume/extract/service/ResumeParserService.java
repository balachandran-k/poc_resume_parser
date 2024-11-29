package com.procyon.resume.extract.service;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Author: balachandran
 * Date: 20/11/24
 */
@Service
public class ResumeParserService {

    private final Tika tika = new Tika();

    // Extract text from a file using Apache Tika
    public String extractTextFromFile(InputStream fileStream) {
        try {
            return tika.parseToString(fileStream);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting text from file", e);
        }
    }

    // Extract fields from text
    public Map<String, Object> extractFields(String resumeText) {
        Map<String, Object> extractedFields = new HashMap<>();

        // Email extraction using regex
        Pattern emailPattern = Pattern.compile("[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}");
        Matcher emailMatcher = emailPattern.matcher(resumeText);
        if (emailMatcher.find()) {
            extractedFields.put("email", emailMatcher.group());
        }

        // Phone number extraction using regex
        Pattern phonePattern = Pattern.compile("\\+?\\d{1,3}[-.()\\s]*\\d{3}[-.()\\s]*\\d{4,}");
        Matcher phoneMatcher = phonePattern.matcher(resumeText);
        if (phoneMatcher.find()) {
            extractedFields.put("phone", phoneMatcher.group());
        }


        // Extract name using OpenNLP
        Pattern namePattern = Pattern.compile("(?<!\\w)([A-Z]+\\s[A-Z]+)(?!\\w)");
        Matcher nameMatcher = namePattern.matcher(resumeText);
        if (nameMatcher.find()) {
            extractedFields.put("name", nameMatcher.group());
        }



        String experienceRegex = "\\b(\\d+(\\.\\d+)?\\+?)\\s*years\\s*of\\b";
        Pattern experiencePattern = Pattern.compile(experienceRegex, Pattern.CASE_INSENSITIVE);
        Matcher experienceMatcher = experiencePattern.matcher(resumeText);

        //List<String> experienceList = new ArrayList<String>();
        if (experienceMatcher.find()) {
            //experienceList.add(experienceMatcher.group(1));
            extractedFields.put("work experience", experienceMatcher.group(1));
        }


        // Skills extraction using predefined keywords
        extractedFields.put("skills", extractSkills(resumeText));

        return extractedFields;
    }

    // Use OpenNLP for name extraction
    /*
    private String extractNameUsingNLP(String text) {
        try {
            ClassPathResource resource = new ClassPathResource("models/en-ner-person.bin");
            TokenNameFinderModel model = new TokenNameFinderModel(resource.getInputStream());
            NameFinderME nameFinder = new NameFinderME(model);

            String[] tokens = SimpleTokenizer.INSTANCE.tokenize(text);
            Span[] nameSpans = nameFinder.find(tokens);

            StringBuilder name = new StringBuilder();
            for (Span span : nameSpans) {
                for (int i = span.getStart(); i < span.getEnd(); i++) {
                    name.append(tokens[i]).append(" ");
                }
            }
            return name.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error extracting name using NLP", e);
        }
    }
    */

    // Extract skills based on predefined keywords
    private List<String> extractSkills(String text) {
        List<String> skills = new ArrayList<>();
        String[] skillKeywords = {"Java", "Python", "C", "C++", "GoLang", "JavaScript", "HTML", "CSS", "Angular", "React", "Spring", "Spring Boot",
                "Microservice", "JPA", "JWT", "OAuth2", "Restfull", "Rest API", "GraphQl", "Spring Security", "Spring Batch", "ELK", "Grafana", "Node.js",
                "MySQL", "PostgreSQL", "MongoDB", "Oracle", "Docker", "Kubernetes", "Jenkins", "Git", "CI/CD",
                "AWS", "AWS S3", "AWS Lambda", "Azure",
                "Google Cloud", "Machine Learning", "TensorFlow", "PyTorch", "Pandas", "Network Security",
                "Penetration Testing", "Firewalls", "SQL", "Excel", "Tableau", "Power BI", "Hadoop", "Apache Spark", "Kafka",
                "MATLAB", "SAS", "SPSS", "Adobe Photoshop", "Illustrator", "Figma", "Wireframing", "Prototyping",
                "Usability Testing", "Adobe Premiere Pro", "Final Cut Pro", "CAD", "MATLAB", "AutoCAD", "SolidWorks", "SEO",
                "SEM", "Google Analytics", "Salesforce", "HubSpot", "Financial Modeling", "QuickBooks", "SAP", "Risk Analysis",
                "Team Management", "Strategic Thinking", "Decision-Making", "Analytical Thinking", "Creativity",
                "Troubleshooting", "Handling Change", "Learning New Tools Quickly", "Collaboration", "Emotional Intelligence",
                "Prioritization", "Multitasking", "Proofreading", "Quality Assurance", "Agile", "Scrum", "Kanban", "JIRA",
                "Trello", "Market Research", "Data Collection", "Academic Research", "Algorithms", "Object-Oriented Programming",
                "Unit Testing", "Predictive Modeling", "Data Cleaning", "Data Visualization", "Campaign Management",
                "Content Creation", "Branding", "Roadmapping", "Stakeholder Management", "A/B Testing",
                "Android", "iOS", "Kotlin", "ReactJs", "Angular",
                "Heroku", "Maven", "GitHub", "Git Action", "Bitbucket", "Struts", "JSF", "Hibernate", "XML", "JSON"};
        for (String skill : skillKeywords) {
            if (text.contains(skill)) {
                skills.add(skill);
            }
        }
        return skills;
    }
}

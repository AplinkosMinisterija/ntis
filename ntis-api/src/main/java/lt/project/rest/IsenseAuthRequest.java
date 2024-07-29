package lt.project.rest;

import java.util.Map;

/**
 * Pagalbinė integracijos su iSense e-identification posisteme klasė, naudojama perduoti papildomus parametrus.
 * 
 */
public record IsenseAuthRequest(Map<String, Object> parameters) {
}

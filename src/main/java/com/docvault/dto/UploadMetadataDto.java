package com.docvault.dto;

import java.util.List;

// ── Upload metadata from multipart request ────────────────────────────────
public class UploadMetadataDto {
    private String       title;
    private String       author;
    private String       department;
    private List<String> tags;
    private String       description;
    private String       documentDate;

    public String       getTitle()                    { return title; }
    public void         setTitle(String v)            { this.title = v; }
    public String       getAuthor()                   { return author; }
    public void         setAuthor(String v)           { this.author = v; }
    public String       getDepartment()               { return department; }
    public void         setDepartment(String v)       { this.department = v; }
    public List<String> getTags()                     { return tags; }
    public void         setTags(List<String> v)       { this.tags = v; }
    public String       getDescription()              { return description; }
    public void         setDescription(String v)      { this.description = v; }
    public String       getDocumentDate()             { return documentDate; }
    public void         setDocumentDate(String v)     { this.documentDate = v; }
}

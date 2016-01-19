package io.iron.springbatch.example;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties
public class Repository {

	private int id;
	@JsonProperty("full_name")
	private String fullName;
	private JsonNode owner;
	@JsonProperty("html_url")
	private String htmlUrl;
	private String description;
	@JsonProperty("created_at")
	private String createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getOwner() {
		return owner.get("login").asText();
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Repository{" +
				"id=" + id +
				", fullName='" + fullName + '\'' +
				", owner='" + getOwner() + '\'' +
				", htmlUrl='" + htmlUrl + '\'' +
				", description='" + description + '\'' +
				", createdAt='" + createdAt + '\'' +
				'}';
	}
}
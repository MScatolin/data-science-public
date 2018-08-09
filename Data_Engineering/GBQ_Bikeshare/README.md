# Project 1 - W205 - Fundamentals of Data Engineering
### UC Berkeley - MIDS
### Marcelo Scatolin Queiroz


#### Note for students using this repo:
Here are the files of my work during my Data engineering class for MIDS. I published them here as a way to encourage self-development and learning. Please, do not copy this files as a full answer to your assignments. This is against Berkeley's code of conduct, highly unethical and the worst, you will not learn as intended.

Feel free to reach out to me with questions, suggestions and critics.

## Introduction

This is a project to introduce the concepts of data extraction and transforming from Google Big Query (GBQ). The chosen dataset was the public available San Francisco bike share files, from the service Ford Go Bikes. You will see that the data set is fairly large, close to 1 million rows, and I enhanced it with some touristic point geographical information to verify GBQ similarities with Microsoft SQL syntax, a tool that I'm more used to.

## Executive Summary:

For running the queries and reporting the findings in one simple UI, I chose to use Jupyter Notebook. For that, I ran the notebook on a Digital Ocean Droplet running Ubuntu where I subscribed to my GBQ account via its `gcloud` tool.

Additionally it is worth noting that most of my queries were saved in a txt file in this repository to improve user readability. The same applies to the queries outputs, saved in a csv format.

For the final report, please see `05-assignment.ipynb`.

## Next steps

The next step should be enhancing my analysis using libraries like `pandas`, `ggplot` and `geoplotlib` to improve users readability and understanding of my findings. This project may be updated soon.

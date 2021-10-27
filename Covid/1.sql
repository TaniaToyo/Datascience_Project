use portfolio;
select * from covid_death
WHERE continent !=''
order by 3,4;

-- Selecting Data
SELECT location, date, total_cases, new_cases, total_deaths, population
FROM covid_death
WHERE continent is not null
ORDER BY 1,YEAR(2);

-- Total Cases Vs Total Death
SELECT location, date, total_cases, total_deaths, population, (total_deaths/total_cases)*100 as percentage
FROM covid_death
WHERE continent is not null
ORDER BY 1,YEAR(2);

-- Total Cases Vs Total Death
-- Likelihood of dying in the United States

SELECT location, date, total_cases, total_deaths, population, (total_deaths/total_cases)*100 as percentage
FROM covid_death
WHERE location like '%states%'
ORDER BY 1,YEAR(2);

-- Total cases Vs Population
-- Shows what percentage of population got covid

SELECT location,  date,total_cases,  population, (total_cases/population)*100 as percentage_of_population_infected
FROM covid_death
-- WHERE location like '%states%'
ORDER BY 1,YEAR(2);

-- Looking at countries with highest infection rate

SELECT location,  MAX(total_cases) as highest_infection_per_location,  population, MAX((total_cases/population)) *100 as percentage_highest_infection_per_location
FROM covid_death
GROUP BY population,location
ORDER BY 4 DESC;

-- Looking at countries with highest death rate

SELECT location,  MAX(cast( total_deaths as float )) as totaldeathcounts
FROM covid_death
WHERE continent !=''
GROUP BY location
ORDER BY totaldeathcounts DESC;

-- CONTINENTS EDA

SELECT continent,  MAX(cast( total_deaths as float )) as totaldeathcounts
FROM covid_death
WHERE continent !=''
GROUP BY continent
ORDER BY totaldeathcounts DESC;

-- Looking into continent with highest infection rate
SELECT population,location
FROM covid_death
GROUP BY population,location;

select * from covid_death
WHERE continent ='Asia'
order by 3,4;

SELECT continent,location,MAX(population) as max_pop 
FROM covid_death
where continent!=''
GROUP BY continent,population,location
ORDER BY 1 ;

-- Showing continent with highest death rate 

SELECT continent,  MAX(cast( total_deaths as float )) as totaldeathcounts
FROM covid_death
WHERE continent !=''
GROUP BY continent
ORDER BY totaldeathcounts DESC;

-- Global Numbers
-- Death of 2% died across the world

SELECT  sum(new_cases), SUM(new_deaths), sum(new_deaths)/sum(new_cases)*100 -- , total_deaths, population, (total_deaths/total_cases)*100 as percentage
FROM covid_death
where continent !=''
-- GROUP BY date
ORDER BY YEAR(1),2;






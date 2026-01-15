<div align="center">
  <div style="display: flex; align-items: center; justify-content: center; gap: 8px;">
    <img src="https://raw.githubusercontent.com/quarkiverse/.github/main/assets/images/quarkus.svg" alt="Quarkus logo" style="height: 70px; width: auto;">
    <img src="https://raw.githubusercontent.com/quarkiverse/.github/main/assets/images/plus-sign.svg" alt="Plus sign" style="height: 70px; width: auto;">
    <img src="https://github.com/quarkiverse/quarkus-batik/blob/main/docs/modules/ROOT/assets/images/batik.svg" alt="Batik logo" style="height: 70px; width: auto;">
  </div>

  <h1>Quarkus Batik</h1>
</div>
<br>

[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.batik/quarkus-batik?logo=apache-maven&style=flat-square)](https://search.maven.org/artifact/io.quarkiverse.batik/quarkus-batik)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](https://opensource.org/licenses/Apache-2.0)
[![Build](https://github.com/quarkiverse/quarkus-batik/actions/workflows/build.yml/badge.svg)](https://github.com/quarkiverse/quarkus-batik/actions/workflows/build.yml)

<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-3-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

## Overview

A Quarkus extension that lets you use [Apache Batik](https://xmlgraphics.apache.org/batik/), a Java-based toolkit for applications or applets that want to use images in the Scalable Vector Graphics (SVG) format for various purposes, such as display, generation or manipulation.

The project’s ambition is to give developers a set of core modules that can be used together or individually to support specific SVG solutions. Examples of modules are the SVG Parser, the SVG Generator and the SVG DOM. Another ambition for the Batik project is to make it highly extensible —for example, Batik allows the developer to handle custom SVG elements. Even though the goal of the project is to provide a set of core modules, one of the deliverables is a full-fledged SVG browser implementation that validates the various modules and their inter-operability.

> [!NOTE]
> The main purpose of this extension is to make Batik work in a native executable built with GraalVM/Mandrel.


## Getting started

Read the full [Batik documentation](https://docs.quarkiverse.io/quarkus-batik/dev/index.html).

### Prerequisite

* Create or use an existing Quarkus application
* Add the Batik with the [Quarkus CLI](https://quarkus.io/guides/cli-tooling):

```bash
quarkus ext add io.quarkiverse.batik:quarkus-batik
```

Or add to your pom.xml directly:

```xml
<dependency>
    <groupId>io.quarkiverse.batik</groupId>
    <artifactId>quarkus-batik</artifactId>
    <version>{project-version}</version>
</dependency>
```


## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://melloware.com"><img src="https://avatars.githubusercontent.com/u/4399574?v=4?s=100" width="100px;" alt="Melloware"/><br /><sub><b>Melloware</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-batik/commits?author=melloware" title="Code">💻</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/gastaldi"><img src="https://avatars.githubusercontent.com/u/54133?v=4?s=100" width="100px;" alt="George Gastaldi"/><br /><sub><b>George Gastaldi</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-batik/commits?author=gastaldi" title="Documentation">📖</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/apupier"><img src="https://avatars.githubusercontent.com/u/1105127?v=4?s=100" width="100px;" alt="Aurélien Pupier"/><br /><sub><b>Aurélien Pupier</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-batik/commits?author=apupier" title="Code">💻</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!

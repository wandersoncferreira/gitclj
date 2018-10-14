# gitclj

A Clojure library designed to provide an interface to Gitlab and
Github. I had to clone all my repositories from Gitlab and I didn't
notice that I had participation in more than 70 repositories. No way.. Let's build something.

## Usage

You need to export your TOKEN_KEY as a environment variable:

``` shell
export apikey=<YOUR-API-KEY>
```

Help o/

``` shell
$ lein run -a
This is a CLI application to handle some necessities in Gitlab/Github.

Usage: lein run [options] action

Options:
-g --gitlab ACTION ARGUMENT

Actions:
  groups                    List all groups in your GitLab
  projects <group-id>       List all projects inside a gitlab group
  clone-all <directory>     Clone all your repositories inside the <directory>

Please, contribute to improve the functionalitites =]
```

My groups:

``` shell
$ lein run -g groups

|     :id |                    :name |                                         :url |
|---------+--------------------------+----------------------------------------------|
| 3558063 |        Captalys Platform |   https://gitlab.com/groups/CaptalysPlatform |
| 2699315 | Captalys Technology Team |       https://gitlab.com/groups/captalysTech |
| 2618190 |            Data Engineer |      https://gitlab.com/groups/infraCaptalys |
| 2618620 |             Data Science |         https://gitlab.com/groups/dsCaptalys |
| 2217155 |        captalysAnalytics | https://gitlab.com/groups/captalys_analytics |
| 1925665 |              captalysNew |        https://gitlab.com/groups/captalysNew |
```

## License

Copyright © 2018 Wanderson Ferreira

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

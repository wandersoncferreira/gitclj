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

Estimate of the number of commits. It's not the real number because it
counts only the **default** branch for each repository. Life is
complicated and some times, dev is the new master. o/

``` shell
wandersonferreira at Wandersons-Air in ~/personal/gitclj
$ lein run -g commit-history,Wanderson
# groups: 6
# repositories: 136

|                    :name | :num-commits |
|--------------------------+--------------|
|              api-pricing |          159 |
|                  weddell |          138 |
|                    coral |          125 |
|                   Hubble |           77 |
|                    Flock |           61 |
|              PyAnalitics |           45 |
|              monitor-ops |           40 |
|              weddell-ETL |           35 |
|                 captaliq |           30 |
|           creditoDigital |           24 |
|              conector-db |           24 |
|       assinatura-digital |           20 |
|              api-billing |           20 |
|                    robot |           19 |
|          api-operacional |           17 |
|    credito-digital-robot |           17 |
|        api-rentabilidade |           14 |
|              tomatico-cd |           13 |
|                  docs-ms |            9 |
|                analytics |            8 |
|               libpartner |            7 |
|                 tomatico |            7 |
|           tomatico-front |            5 |
|       credito-digital-os |            5 |
|                  pioneer |            4 |
|                api-login |            4 |
|                   Saturn |            3 |
| credito-digital-servicos |            3 |
|                 sputnik1 |            2 |
|               manifestos |            2 |
|   kong-validation-plugin |            2 |
|      hub-credito-digital |            2 |
|                 devopsV1 |            1 |
|          product-manager |            1 |
|             bookbuilding |            1 |
|                capcustod |            1 |
| assinatura-digital-front |            1 |
|                  moip-cd |            1 |
```


## License

Copyright © 2018 Wanderson Ferreira

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

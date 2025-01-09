const core = require("@actions/core");
const github = require("@actions/github");

const member= {
    cmj7271: "740776211231277066",
    ybkang1108: "1038826581558296636",
    yeeunli: "1082157260249251870",
    JaehoonJung0430: "1007395745223151726"
}

function selectRandomReviewer() {
    const candidate = Object.keys(member).filter(
        (name) => name !== github.context.actor
    )
    return candidate[
        Math.floor(Math.random() * candidate.length)
        ]
}

async function sendDiscordMsg(reviewer, title) {
    const webhook = process.env.DISCORD_WEBHOOK;

    const msg = {
        content: createMsg(reviewer, title)
    }

    await fetch(webhook, {
        method: "POST",
        headers: { 'Content-type': 'application/json' },
        body: JSON.stringify(msg)
    })
}

function createMsg(reviewer, title) {
    return title + "\n" + "* PR: " + `https://github.com/${github.context.repo.owner}/${github.context.repo.repo}/pulls/${github.context.payload.pull_request.number}`
        + "\n* 담당자: " + "<@!" + member[reviewer] + ">"
}

async function main() {
    const githubClient = github.getOctokit(process.env.REVIEW_TOKEN);
    const reviewer = selectRandomReviewer();

    const { owner, repo } = github.context.repo;
    const pr_info = {
        owner: owner,
        repo: repo,
        pull_number: github.context.payload.pull_request.number
    }

    const requested_reviewers = await githubClient.rest.pulls.listRequestedReviewers(pr_info)

    if(requested_reviewers.data.users.length === 0) {
        githubClient.rest.pulls.requestReviewers(
            {
                ...pr_info,
                reviewers: [reviewer]
            }
        )
            .then((res) => console.log("reviewer assign success: ", res))
            .catch((err) => {
                console.log("reviewer assign failed:", err);
                process.exit(1);
            });
    } else {console.log("already assigned reviewer exist.")}

    const pr = await githubClient.rest.pulls.get(
        {
            owner: owner,
            repo: repo,
            pull_number: github.context.payload.pull_request.number
        }
    )

    sendDiscordMsg(reviewer, pr.data.title)
        .then(() => console.log("message send success"))
        .catch(() => {
            console.log("message send failed");
            process.exit(1);
        });
}

main().then(() => console.log("success")).catch(() => {
    console.log("failed")
    process.exit(1);
});